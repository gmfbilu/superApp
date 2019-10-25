package org.gmfbilu.superapp.lib_base.utils;

import android.os.Looper;
import android.widget.Toast;

import org.gmfbilu.superapp.lib_base.base.BaseApplication;

public class ToastUtil {

    private ToastUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void show(String message) {
        if (message == null || message.length() == 0) {
            return;
        }
        if (Looper.getMainLooper() != Looper.myLooper()) {
            LoggerUtil.d("在子线程使用Toast");
        }
        Toast.makeText(BaseApplication.mApplicationContext, message, Toast.LENGTH_SHORT).show();
    }
}
