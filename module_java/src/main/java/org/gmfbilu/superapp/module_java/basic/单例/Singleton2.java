package org.gmfbilu.superapp.module_java.basic.单例;

/**
 * 懒汉式线程安全,效率低.单例一直没用过，但是这个东西还是一直占用我的内存
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
