package org.gmfbilu.lib_base.base;

import android.app.Application;
import android.os.Process;
import android.text.TextUtils;

import com.tencent.bugly.crashreport.CrashReport;

import org.gmfbilu.lib_base.BuildConfig;
import org.gmfbilu.lib_base.utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        initCrashReport();
    }

    private void initCrashReport() {
        // 获取当前包名
        String packageName = getApplicationContext().getPackageName();
        // 获取当前进程名
        String processName = getProcessName(Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        // 设置是否为上报进程,如果App使用了多进程且各个进程都会初始化Bugly.那么每个进程下的Bugly都会进行数据上报，造成不必要的资源浪费.只在主进程下上报数据
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppChannel("myChannel");  //设置渠道
        strategy.setAppVersion("1.0.1");      //App的版本
        strategy.setAppPackageName(packageName);  //App的包名
        strategy.setAppReportDelay(10000);   //Bugly会在启动10s后联网同步数据
        CrashReport.initCrashReport(this, "e5ab76a7fa", BuildConfig.DEBUG, strategy);
        CrashReport.setIsDevelopmentDevice(this, BuildConfig.DEBUG);
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
