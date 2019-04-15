package org.gmfbilu.superapp.lib_base.app;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;

import org.gmfbilu.superapp.lib_base.BuildConfig;

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
    public static void start(Context context) {
        Log.d(Constant.LOG_NAME, "ApplicationIntentService ---> start");
        Intent intent = new Intent(context, ApplicationIntentService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    /**
     * 此方法为异步方法
     * 多次去启动IntentService,onHandleIntent会启动多次
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // TODO: 3/6/19 有bug,可能此方法还没有完全回调完成，但是界面已经显示完成，用户点击控件的时候，第三方还没有完全初始化
        initLogger();
        initLocalCrashReport();
        initARouter();
        initLeakCanary();
        Log.d(Constant.LOG_NAME, "ApplicationIntentService ---> onHandleIntent");
    }


    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(Constant.LOG_NAME)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                //return BuildConfig.DEBUG;
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


    private void initLocalCrashReport() {
        CrashHandler.getInstance().init(getApplicationContext());
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(getApplication());
    }


    /**
     * 多次去启动IntentService,onStartCommand会启动多次
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constant.LOG_NAME, "ApplicationIntentService ---> onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }


}