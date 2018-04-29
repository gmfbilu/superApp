package org.gmfbilu.superapp.lib_base.app;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.bugly.crashreport.CrashReport;

import org.gmfbilu.superapp.lib_base.BuildConfig;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;

/**
 * 我们多次启动IntentService，但IntentService的实例只有一个，这跟传统的Service是一样的，最终IntentService会去调用onHandleIntent执行异步任务
 */


public class ApplicationIntentService extends IntentService {


    public ApplicationIntentService() {
        super("ApplicationIntentService");
    }

    private static final String ACTION_INIT_WHEN_APP_CREATE = Constant.BASE_PACKAGENAME;

    /**
     * 多次去启动IntentService,onCreate只会启动一次
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constant.LOG_NAME, "ApplicationIntentService ---> onCreate");
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();
            startForeground(1, notification);
        }
    }

    /**
     * 多次去启动IntentService,start会启动多次
     */
    public static void start(Context context, String tagWho) {
        Intent intent = new Intent(context, ApplicationIntentService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
        Log.d(Constant.LOG_NAME, tagWho + " starts ApplicationIntentService ---> start");
    }

    /**
     * 此方法为异步方法
     * 多次去启动IntentService,onHandleIntent会启动多次
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        initLogger();
        initLocalCrashReport();
        initARouter();
        initCrashReport();
        Log.d(Constant.LOG_NAME, "ApplicationIntentService ---> onHandleIntent");
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                // .methodCount(2)         // (Optional) How many method line to show. Default 2
                //.methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
                //.logStrategy() // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(Constant.LOG_NAME)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return Constant.ISSHOWLOG;
            }

        });
    }


    private void initARouter() {
        if (BuildConfig.DEBUG) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(getApplication());
    }


    private void initCrashReport() {
        String packageName = AppUtils.getPackageName(getApplicationContext());
        String processName = AppUtils.getProcessName(Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        // 设置是否为上报进程,如果App使用了多进程且各个进程都会初始化Bugly.那么每个进程下的Bugly都会进行数据上报，造成不必要的资源浪费.只在主进程下上报数据
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppChannel(AppUtils.getChannelName(getApplicationContext()));  //设置渠道
        strategy.setAppVersion(AppUtils.getAppVersion(getApplicationContext()));      //App的版本
        strategy.setAppPackageName(packageName);  //App的包名
        strategy.setAppReportDelay(10000);   //Bugly会在启动10s后联网同步数据
        CrashReport.initCrashReport(getApplicationContext(), Constant.BUGLY_APPID, !Constant.ISRELEASE, strategy);
        CrashReport.setIsDevelopmentDevice(getApplicationContext(), !Constant.ISRELEASE);
    }

    private void initLocalCrashReport() {
        CrashHandler.getInstance().init(getApplicationContext());
    }


    /**
     * 多次去启动IntentService,onStartCommand会启动多次
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Constant.LOG_NAME, "ApplicationIntentService ---> onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constant.LOG_NAME, "ApplicationIntentService ---> onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Constant.LOG_NAME, "ApplicationIntentService ---> onBind");
        return super.onBind(intent);
    }


}