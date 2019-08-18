package org.gmfbilu.superapp.module_java.basic.设计模式.工厂.抽象工厂;

public abstract class AbstractFactory {

    protected abstract Car getCar();

    /**
     * 动态配置的功能
     * 固定模式的委派
     */
    public Car getCar(String name) {
        if (name.equals("BMW")) {
            return new BMWFactory().getCar();
        } else if (name.equals("Benz")) {
            return new BenzFactory().getCar();
        } else {
            return null;
        }
    }
}
