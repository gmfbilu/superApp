package org.gmfbilu.superapp.lib_base.utils.sharedPreferences;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SpBlockHelper {


    static final String TAG = "SpBlockHelper";
    static boolean init = false;
    static String CLASS_QUEUED_WORK = "android.app.QueuedWork";
    static String FIELD_PENDING_FINISHERS = "sPendingWorkFinishers";
    static ConcurrentLinkedQueue<Runnable> sPendingWorkFinishers = null;

    public static void beforeSPBlock(String tag) {
        if (!init) {
            getPendingWorkFinishers();
            init = true;
        }
        //Logger.d(TAG, "beforeSPBlock " + tag);
        if (sPendingWorkFinishers != null) {
            sPendingWorkFinishers.clear();
        }
    }


    static void getPendingWorkFinishers() {
        try {
            Class clazz = Class.forName(CLASS_QUEUED_WORK);
            Field field = clazz.getDeclaredField(FIELD_PENDING_FINISHERS);
            if (field != null) {
                field.setAccessible(true);
                sPendingWorkFinishers = (ConcurrentLinkedQueue<Runnable>) field.get(null);
            }
        } catch (Exception e) {
            //.e(TAG, "getPendingWorkFinishers", e);
        }
    }
}
