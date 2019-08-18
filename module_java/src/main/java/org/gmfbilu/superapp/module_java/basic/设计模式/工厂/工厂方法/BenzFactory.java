package org.gmfbilu.superapp.module_java.basic.设计模式.工厂.工厂方法;

/**
 * 奔驰工厂
 */
public class BenzFactory implements Factory {

    @Override
    public Car getCar() {
        return new Benz();
    }
}
