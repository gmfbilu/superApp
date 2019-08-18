package org.gmfbilu.superapp.module_java.basic.设计模式.工厂.抽象工厂;


/**
 * 宝马工厂
 */
public class BMWFactory extends AbstractFactory {
    @Override
    public Car getCar() {
        return new BMW();
    }
}
