package org.gmfbilu.superapp.module_java.basic.singleton;

/**
 * 双重校验锁,避免在多线程环境中被创建多次
 */
public class Singleton7 {
    private volatile static Singleton7 singleton;

    private Singleton7() {
    }

    public static Singleton7 getSingleton7() {
        if (singleton == null) {
            synchronized (Singleton7.class) {
                if (singleton == null) {
                    singleton = new Singleton7();
                }
            }
        }
        return singleton;
    }
}
