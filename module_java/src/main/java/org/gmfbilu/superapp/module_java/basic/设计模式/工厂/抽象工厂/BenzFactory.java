package org.gmfbilu.superapp.module_java.basic.设计模式.工厂.抽象工厂;


/**
 * 奔驰工厂
 */
public class BenzFactory extends AbstractFactory {

    @Override
    public Car getCar() {
        return new Benz();
    }
}
