package org.gmfbilu.superapp.lib_base.utils;

import android.os.Build;

public class AndroidVersion {

    /**
     * 判断当前手机系统版本API是否是16以上。
     *
     * @return 16以上返回true，否则返回false。
     */
    public static boolean isJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * 判断当前手机系统版本API是否是17以上。
     *
     * @return 17以上返回true，否则返回false。
     */
    public static boolean isJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * 判断当前手机系统版本API是否是18以上。
     *
     * @return 18以上返回true，否则返回false。
     */
    public static boolean isJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * 判断当前手机系统版本API是否是19以上。
     *
     * @return 19以上返回true，否则返回false。
     */
    public static boolean isKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 判断当前手机系统版本API是否是21以上。
     *
     * @return 21以上返回true，否则返回false。
     */
    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 判断当前手机系统版本API是否是22以上。
     *
     * @return 22以上返回true，否则返回false。
     */
    public static boolean isLollipopMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    /**
     * 判断当前手机系统版本API是否是23以上。
     *
     * @return 23以上返回true，否则返回false。
     */
    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 判断当前手机系统版本API是否是24以上。
     *
     * @return 24以上返回true，否则返回false。
     */
    public static boolean isNougat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }
}
