package org.gmfbilu.superapp.module_java.basic.设计模式.代理模式.动态代理;

public class Test {

    public static void main(String[] a) {
        ISubject subject = new RealSubject();
        ISubject proxy = new DynamicProxyHandler(subject).getProxy();
        proxy.doAction("play");
    }
}
