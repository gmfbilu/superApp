package org.gmfbilu.neteasycloudcourse.ui.屏幕适配.像素密度;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * 像素密度
 * 在Activity中的onCreate中调用Density.setDensity，在setContentView之前调用，当前Activity中的控件可以直接使用dp，例如360dp就是充满屏幕宽度
 * 也可以在Application的onCreate中调用registerActivityLifecycleCallbacks，在onActivityCreated中调用，对整个app起作用
 */
public class Density {

    private static final float WIDTH = 360;//参考设备宽度，单位dp

    private static float appDensity;//当前屏幕密度
    private static float appScaleDensity;//字体缩放比例，默认就是appDensity

    public static void setDensity(Application application, Activity activity) {
        //获取当前app的屏幕显示信息
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (appDensity == 0) {
            appDensity = displayMetrics.density;
            appScaleDensity = displayMetrics.scaledDensity;

            //用户更改系统设置，字体监听
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        //子体发生了更改，重新对appScaleDensity赋值
                        appScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        //计算目标值
        float targetDensity = displayMetrics.widthPixels / WIDTH; // 1080/360=3.0
        float targetScaleDensity = targetDensity * (appScaleDensity / appDensity);
        int targetDensityDpi = (int) (targetDensity * 160);

        //替换Activity的density,scaleDensity,densityDpi
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        dm.density = targetDensity;
        dm.scaledDensity = targetScaleDensity;
        dm.densityDpi = targetDensityDpi;
    }
}
