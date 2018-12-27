package org.gmfbilu.superapp.module_java.singleton;

/**
 * 懒汉式非线程安全
 */
public class Singleton1 {

    private static Singleton1 sSingleton1;

    private Singleton1() {
    }

    public static Singleton1 getSingleton1() {
        if (sSingleton1==null){
            sSingleton1 = new Singleton1();
        }
        return sSingleton1;
    }
}
