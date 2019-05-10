package org.gmfbilu.superapp.module_java.basic.singleton;

/**
 * 懒汉式非线程安全,单例一直没用过的情况下不占用内存
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
