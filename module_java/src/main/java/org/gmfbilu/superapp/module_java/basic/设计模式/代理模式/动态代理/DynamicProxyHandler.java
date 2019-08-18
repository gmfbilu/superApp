package org.gmfbilu.superapp.module_java.basic.设计模式.代理模式.动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyHandler implements InvocationHandler {

    //target就是ISubject
    private Object target;

    public DynamicProxyHandler(Object target) {
        this.target = target;
    }

    //T就是ISubject
    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("办事之前先收取点费用");
        System.out.println("开始办事");
        Object result = null;
        try {
            result = method.invoke(target, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("办完了");
        return result;
    }
}
