package org.gmfbilu.superapp.module_java.basic.设计模式.单例;

/**
 * 静态内部类：推荐
 * 类被装载了，实例不一定被初始化。因为SingletonHolder类没有被主动使用，只有显示通过调用getInstance方法时，才会显示装载SingletonHolder类
 */
public class Singleton5 {

    private static class Singleton5Holder {
        private static final Singleton5 sSingleton5 = new Singleton5();
    }

    private Singleton5() {
    }

    public static final Singleton5 getSingleton5() {
        return Singleton5Holder.sSingleton5;
    }
}
