package org.gmfbilu.superapp.module_java.basic.设计模式.工厂.工厂方法;

public class Test {

    public static void main(String[] args) {
        Factory bmwFactory = new BMWFactory();
        System.out.println(bmwFactory.getCar().getName());
        Factory benzFactory = new BenzFactory();
        System.out.println(benzFactory.getCar().getName());
    }
}
