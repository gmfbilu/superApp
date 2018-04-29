package org.gmfbilu.superapp.lib_base.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ActivitiesManager {

    // 当前与用户进行交互的Activity
    private static WeakReference<Activity> sCurrentActivityWeakRef;

    /**
     * 存放activity的列表
     */
    public static HashMap<Class<?>, Activity> activities = new LinkedHashMap<>();

    /**
     * 添加Activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity, Class<?> clz) {
        activities.put(clz, activity);
    }

    /**
     * 判断一个Activity 是否存在
     *
     * @param clz
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static <T extends Activity> boolean isActivityExist(Class<T> clz) {
        boolean res;
        Activity activity = getActivity(clz);
        if (activity == null) {
            res = false;
        } else {
            res = !(activity.isFinishing() || activity.isDestroyed());
        }

        return res;
    }

    /**
     * 获得指定activity实例
     *
     * @param clazz Activity 的类对象
     * @return
     */
    public static <T extends Activity> T getActivity(Class<T> clazz) {
        return (T) activities.get(clazz);
    }

    /**
     * 移除activity,代替finish
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (activities.containsValue(activity)) {
            activities.remove(activity.getClass());
        }
    }

    /**
     * 移除所有的Activity
     */
    public static void removeAllActivity() {
        if (activities != null && activities.size() > 0) {
            Set<Map.Entry<Class<?>, Activity>> sets = activities.entrySet();
            for (Map.Entry<Class<?>, Activity> s : sets) {
                if (!s.getValue().isFinishing()) {
                    s.getValue().finish();
                }
            }
            activities.clear();
        }
    }


    /**
     * 获取当前与用户交互的Activity,例如弹出dialog，popupwindow
     *
     * @return
     */
    public static Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public static void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<>(activity);
    }

}
