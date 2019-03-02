package org.gmfbilu.superapp.lib_base.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.orhanobut.logger.Logger;

import org.gmfbilu.superapp.lib_base.app.ApplicationIntentService;
import org.gmfbilu.superapp.lib_base.app.Constant;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;

import java.util.Objects;

/**
 * Created by gmfbilu on 2018/3/2.
 * <p>
 * 要想使用BaseApplication，必须在组件中实现自己的Application，并且继承BaseApplication；
 * 组件中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用；
 * 组件的Application需置于java/debug文件夹中，不得放于主代码；
 */

public class BaseApplication extends Application {

    public static Context mApplicationContext;

    /**
     * Application可能会调用多次，特别是集成推送情况下
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if (!Objects.requireNonNull(AppUtils.getProcessName()).contains(":")) {
            mApplicationContext = this;
            ApplicationIntentService.start(this);
        }
        Log.d(Constant.LOG_NAME, getClass().getName() + "----" + AppUtils.getProcessName() + "---> start");
    }

    /**
     * 模拟器上app退出会回调这个方法
     * 真机上不会
     */
    @Override
    public void onTerminate() {
        Logger.d(getClass().getName() + "---> onTerminate");
        super.onTerminate();
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
