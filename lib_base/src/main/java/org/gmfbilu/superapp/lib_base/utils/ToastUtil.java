package org.gmfbilu.superapp.lib_base.utils;

import android.widget.Toast;

import org.gmfbilu.superapp.lib_base.base.BaseApplication;

public class ToastUtil {

    public static void show(String message) {
        Toast.makeText(BaseApplication.mApplicationContext, message, Toast.LENGTH_SHORT).show();
    }
}
