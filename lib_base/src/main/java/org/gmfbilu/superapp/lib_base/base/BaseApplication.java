package org.gmfbilu.superapp.lib_base.base;

import android.app.Application;
import android.os.Process;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.bugly.crashreport.CrashReport;

import org.gmfbilu.superapp.lib_base.app.Constant;
import org.gmfbilu.superapp.lib_base.app.CrashHandler;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.lib_base.utils.Utils;

import me.yokeyword.fragmentation.Fragmentation;

/**
 * Created by gmfbilu on 2018/3/2.
 * <p>
 * 组件中获取Context的方法必须为:Utils.getContext()，不允许其他写法；
 * 要想使用BaseApplication，必须在组件中实现自己的Application，并且继承BaseApplication；
 * 组件中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用；
 * 组件的Application需置于java/debug文件夹中，不得放于主代码；
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        initARouter();
        initCrashReport();
        initFragmentation();
        initLogger();
        CrashHandler.getInstance().init(this);
    }

    private void initARouter() {
        if (Utils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                //.showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
               // .methodCount(0)         // (Optional) How many method line to show. Default 2
               // .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
                //.logStrategy() // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("SuperApp")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    private void initFragmentation() {
        Fragmentation.builder()
                // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(!Constant.ISRELEASE) // 实际场景建议.debug(BuildConfig.DEBUG)
                /**
                 * 可以获取到{@link me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning}
                 * 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
                 */
                .handleException(e -> {
                    // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
                    // Bugtags.sendException(e);
                })
                .install();
    }

    private void initCrashReport() {
        String packageName = AppUtils.getPackageName();
        String processName = AppUtils.getProcessName(Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        // 设置是否为上报进程,如果App使用了多进程且各个进程都会初始化Bugly.那么每个进程下的Bugly都会进行数据上报，造成不必要的资源浪费.只在主进程下上报数据
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppChannel(AppUtils.getChannelName());  //设置渠道
        strategy.setAppVersion(AppUtils.getAppVersion());      //App的版本
        strategy.setAppPackageName(packageName);  //App的包名
        strategy.setAppReportDelay(10000);   //Bugly会在启动10s后联网同步数据
        CrashReport.initCrashReport(this, "e5ab76a7fa",!Constant.ISRELEASE, strategy);
        CrashReport.setIsDevelopmentDevice(this, !Constant.ISRELEASE);
    }

}
