package org.gmfbilu.superapp.module_java.basic.java8;

public interface Java8Interface {

    void method1();

    default void method2() {

    }

    static void method3() {
        System.out.print("Java8及以后接口有main方法了");
    }

    static void main(String args[]) {
        method3();
    }
}
