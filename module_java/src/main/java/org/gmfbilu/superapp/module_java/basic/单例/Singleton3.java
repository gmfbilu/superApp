package org.gmfbilu.superapp.module_java.basic.单例;

/**
 * 饿汉式
 * 这种方式基于classloder机制避免了多线程的同步问题，instance在类装载时就实例化，所以达不到懒加载的效果
 */
public class Singleton3 {

    private static Singleton3 sSingleton3 = new Singleton3();

    private Singleton3() {
    }

    public static Singleton3 getSingleton3() {
        return sSingleton3;
    }
}
