package org.gmfbilu.superapp;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

import org.gmfbilu.lib_base.utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by gmfbilu on 2018/3/8.
 * SuperApplication的代理类,所有之前在SuperApplication的onCreate()里初始化代码现在只要在
 * ApplicationLike里面的onCreate()里面初始化，插件会动态替换AndroidMinifest文件中的Application
 * 为我们定义好用于反射真实Application的类
 */

public class ApplicationLike extends DefaultApplicationLike {


    public static final String TAG = "Tinker.SampleApplicationLike";

    public ApplicationLike(Application application, int tinkerFlags,
                           boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                           long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(getApplication());
        initARouter();
        initBuglyHotFix();
        initCrashReport();
    }

    private void initCrashReport() {
        // 获取当前包名
        String packageName = getApplication().getPackageName();
        // 获取当前进程名
        String processName = getProcessName(Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplication());
        // 设置是否为上报进程,如果App使用了多进程且各个进程都会初始化Bugly.那么每个进程下的Bugly都会进行数据上报，造成不必要的资源浪费.只在主进程下上报数据
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppChannel("myChannel");  //设置渠道
        strategy.setAppVersion("1.0.1");      //App的版本
        strategy.setAppPackageName(packageName);  //App的包名
        strategy.setAppReportDelay(10000);   //Bugly会在启动10s后联网同步数据
        CrashReport.initCrashReport(getApplication(), "e5ab76a7fa", org.gmfbilu.lib_base.BuildConfig.DEBUG, strategy);
        CrashReport.setIsDevelopmentDevice(getApplication(), org.gmfbilu.lib_base.BuildConfig.DEBUG);
    }

    private void initBuglyHotFix() {
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁，默认为true
        Beta.canAutoDownloadPatch = true;
        // 设置是否自动合成补丁，默认为true
        Beta.canAutoPatch = true;
        // 设置是否提示用户重启，默认为false
        Beta.canNotifyUserRestart = true;
        // 补丁回调接口
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFile) {
                Toast.makeText(getApplication(), "补丁下载地址" + patchFile, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
                Toast.makeText(getApplication(),
                        String.format(Locale.getDefault(), "%s %d%%",
                                Beta.strNotificationDownloading,
                                (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadSuccess(String msg) {
                Toast.makeText(getApplication(), "补丁下载成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadFailure(String msg) {
                Toast.makeText(getApplication(), "补丁下载失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onApplySuccess(String msg) {
                Toast.makeText(getApplication(), "补丁应用成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onApplyFailure(String msg) {
                Toast.makeText(getApplication(), "补丁应用失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPatchRollback() {

            }
        };

        // 设置开发设备，默认为false，上传补丁如果下发范围指定为“开发设备”，需要调用此接口来标识开发设备
        //Bugly.setIsDevelopmentDevice(getApplication(), true);
        // 多渠道需求塞入
        // String channel = WalleChannelReader.getChannel(getApplication());
        // Bugly.setAppChannel(getApplication(), channel);
        //是否开启debug模式
        Bugly.init(getApplication(), "e5ab76a7fa", false);
    }

    private void initARouter() {
        if (Utils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(getApplication());
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Beta.unInit();
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
