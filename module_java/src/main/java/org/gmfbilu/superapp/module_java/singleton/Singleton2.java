package org.gmfbilu.superapp.module_java.singleton;

/**
 * 懒汉式线程安全,效率低
 */
public class Singleton2 {

    private static Singleton2 sSingleton2;

    private Singleton2() {
    }

    public static synchronized Singleton2 getSingleton2() {
        if (sSingleton2 == null) {
            sSingleton2 = new Singleton2();
        }
        return sSingleton2;
    }
}
