package org.gmfbilu.superapp.lib_base.utils;

import com.orhanobut.logger.Logger;

public class LoggerUtil {

    private LoggerUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void d(Object object) {
        Logger.d(object);
    }

    public static void i(String tag, Object object) {
        Logger.i(tag, object);
    }

}
