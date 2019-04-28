package org.gmfbilu.superapp.lib_base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class RxPermissionsUtils {

    @SuppressLint("CheckResult")
    public static void isPermissions(Activity activity, final TCallback<Boolean> callback, final String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(permissions).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (!aBoolean) {
                    //no_permissions
                }
                callback.callback(aBoolean);
            }
        });
    }

}


