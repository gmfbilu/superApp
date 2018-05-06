package org.gmfbilu.superapp;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.gmfbilu.superapp.lib_base.app.ARouterPath;
import org.gmfbilu.superapp.lib_base.app.ApplicationIntentService;
import org.gmfbilu.superapp.lib_base.app.Constant;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;

import java.util.Locale;

/**
 * Created by gmfbilu on 2018/3/8.
 * SuperApplication的代理类,所有之前在SuperApplication的onCreate()里初始化代码现在只要在
 * ApplicationLike里面的onCreate()里面初始化，插件会动态替换AndroidMinifest文件中的Application
 * 为我们定义好用于反射真实Application的类
 */

public class ApplicationLike extends DefaultApplicationLike {

    private Application application = getApplication();


    public ApplicationLike(Application application,
                           int tinkerFlags,
                           boolean tinkerLoadVerifyFlag,
                           long applicationStartElapsedTime,
                           long applicationStartMillisTime,
                           Intent tinkerResultIntent) {
        super(application,
                tinkerFlags,
                tinkerLoadVerifyFlag,
                applicationStartElapsedTime,
                applicationStartMillisTime,
                tinkerResultIntent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationIntentService.start(getApplication(), "ApplicationLike");
        initBuglyHotFix();
        /**
         * 并不是所有的第三方库都可以放在IntentService中
         */
        initPush();
        Log.d(Constant.LOG_NAME, getClass().getName() + "---> start");

    }

    private void initPush() {
        UMConfigure.init(getApplication(), Constant.PUSH_APPKEY, AppUtils.getChannelName(getApplication()), UMConfigure.DEVICE_TYPE_PHONE, Constant.PUSH_SECRET);
        UMConfigure.setLogEnabled(Constant.ISSHOWLOG);
        PushAgent mPushAgent = PushAgent.getInstance(getApplication());
        /**
         * 注册推送服务，每次调用register方法都会回调该接口
         */
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.d(Constant.LOG_NAME, "推送服务注册成功，deviceToken：" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.d(Constant.LOG_NAME, "推送服务注册失败，错误信息：" + s + ", " + s1);
            }
        });
        /**
         * 自定义通知打开动作
         * 自定义行为的数据放在UMessage.custom字段
         */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void dealWithCustomAction(Context context, UMessage uMessage) {
                super.dealWithCustomAction(context, uMessage);
                Logger.d(uMessage.custom);
                switch (uMessage.custom) {
                    case "module_main_googleLibrary":
                        //打开Activity
                        ARouter.getInstance()
                                .build(ARouterPath.MODULE_GOOGLELIBRARY)
                                //.withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                                .navigation();

                        break;

                }
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

    }


    private void initBuglyHotFix() {
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁，默认为true
        Beta.canAutoDownloadPatch = true;
        // 设置是否自动合成补丁，默认为true
        Beta.canAutoPatch = true;
        // 设置是否提示用户重启，默认为false
        Beta.canNotifyUserRestart = false;
        // 补丁回调接口
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFile) {
                Toast.makeText(application, "补丁下载地址" + patchFile, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
                Toast.makeText(application,
                        String.format(Locale.getDefault(), "%s %d%%",
                                Beta.strNotificationDownloading,
                                (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadSuccess(String msg) {
                Toast.makeText(application, "补丁下载成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadFailure(String msg) {
                Toast.makeText(application, "补丁下载失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onApplySuccess(String msg) {
                Toast.makeText(application, "补丁应用成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onApplyFailure(String msg) {
                Toast.makeText(application, "补丁应用失败", Toast.LENGTH_SHORT).show();
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
        Bugly.init(application, "e5ab76a7fa", true);
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
        // 程序终止的时候执行
        Logger.d(getClass().getName() + "---> onTerminate");
        super.onTerminate();
        Beta.unInit();
    }

    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        Logger.d(getClass().getName() + "---> onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        Logger.d(getClass().getName() + "---> onTrimMemory");
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Logger.d(getClass().getName() + "---> onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

}
