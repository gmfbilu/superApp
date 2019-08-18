package org.gmfbilu.superapp.module_java.basic.设计模式.工厂.工厂方法;

/**
 * 宝马工厂
 */
public class BMWFactory implements Factory {
    @Override
    public Car getCar() {
        return new BMW();
    }
}
