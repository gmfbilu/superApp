package org.gmfbilu.superapp.module_java.basic.代理模式.静态代理;

public class Test {

    public static void main(String[] a) {
        RealSubject realSubject = new RealSubject();
        ProxySubject proxySubject = new ProxySubject(realSubject);
        proxySubject.doAction("play");
    }
}
