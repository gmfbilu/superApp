package org.gmfbilu.superapp.module_java.basic.单例;

/**
 * 饿汉式，和第三种差不多
 */
public class Singleton4 {
    private static Singleton4 sSingleton4 = null;

    private Singleton4() {
    }

    static {
        sSingleton4 = new Singleton4();
    }

    public static Singleton4 getSingleton4() {
        return sSingleton4;
    }
}
