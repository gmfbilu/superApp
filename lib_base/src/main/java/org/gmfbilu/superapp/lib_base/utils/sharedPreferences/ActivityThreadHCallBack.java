package org.gmfbilu.superapp.lib_base.utils.sharedPreferences;

import android.os.Handler;
import android.os.Message;

public class ActivityThreadHCallBack implements Handler.Callback {


    private static final int SERVICE_ARGS = 115;
    private static final int STOP_SERVICE = 116;
    private static final int SLEEPING = 137;
    private static final int STOP_ACTIVITY_SHOW = 103;
    private static final int STOP_ACTIVITY_HIDE = 104;
    private static final int ENTER_ANIMATION_COMPLETE = 149;

    @Override
    public boolean handleMessage(Message msg) {
        final int what = msg.what;
        switch (what) {
            case SERVICE_ARGS:
                SpBlockHelper.beforeSPBlock("SERVICE_ARGS");
                break;
            case STOP_SERVICE:
                SpBlockHelper.beforeSPBlock("STOP_SERVICE");
                break;
            case SLEEPING:
                SpBlockHelper.beforeSPBlock("SLEEPING");
                break;
            case STOP_ACTIVITY_SHOW:
            case STOP_ACTIVITY_HIDE:
                SpBlockHelper.beforeSPBlock("STOP_ACTIVITY");
                break;
        }
        return false;
    }
}
