package org.gmfbilu.superapp.module_java.basic.代理模式.动态代理;

public class RealSubject implements ISubject {

    @Override
    public void doAction(String action) {
        System.out.println("I am RealSubject, do action "+ action);
    }
}
