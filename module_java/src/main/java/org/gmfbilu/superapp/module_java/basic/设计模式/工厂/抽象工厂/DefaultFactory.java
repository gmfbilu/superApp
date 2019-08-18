package org.gmfbilu.superapp.module_java.basic.设计模式.工厂.抽象工厂;

/**
 * 默认工厂
 */
public class DefaultFactory extends AbstractFactory {

    private BenzFactory defaultFactory = new BenzFactory();

    @Override
    protected Car getCar() {
        return defaultFactory.getCar();
    }
}
