package org.gmfbilu.superapp.module_java.basic.代理模式.静态代理;

public class ProxySubject implements ISubject {

    ISubject mRealSubject;

    public ProxySubject(ISubject mRealSubject) {
        super();
        this.mRealSubject = mRealSubject;
    }


    @Override
    public void doAction(String action) {
        preRequest();
        mRealSubject.doAction(action);
        postRequest();
    }

    protected void postRequest() {
        System.out.println("postRequest");
    }

    protected void preRequest() {
        System.out.println("preRequest");
    }
}

