package org.gmfbilu.superapp.module_java.basic.设计模式.工厂.简单工厂;

/**
 * 属于创建型设计模式，需要生成的对象叫做产品 ，生成对象的地方叫做工厂
 * 在任何需要生成复杂对象的地方，都可以使用工厂方法模式。直接用new可以完成的不需要用工厂模式
 * <p>
 * 简单（静态）工厂：
 * 简单工厂它是一个具体的类，非接口或抽象类，有一个重要的create()方法，利用if或者 switch创建产品并返回
 * create()方法通常是静态的，所以也称之为静态工厂
 * 缺点：扩展性差（我想增加一种车，除了新增一个车产品类，还需要修改简单工厂类方法）。不同的产品需要不同额外参数的时候 不支持
 * <p>
 * 工厂方法：
 * 工厂方法就是把简单工厂中具体的工厂类，划分成两层：抽象工厂层(Factory)+具体的工厂子类层(BMWFactory)
 * 当需求变化，只需要增删相应的类，不需要修改已有的类。
 * 缺点是引入抽象工厂层后，每次新增一个具体产品类，也要同时新增一个具体工厂类
 * <p>
 * 抽象工厂：
 * 将工厂也抽象了，在使用时，工厂和产品都是面向接口编程
 * 缺点是将工厂也抽象后，就是类爆炸了。每次拓展新产品种类需要修改抽象工厂类，因此所有的具体工厂子类，都被牵连，需要同步被修改
 */
public class SimpleFactory {

    public static void main(String[] args) {
        Car bmw = SimpleFactory.getCar("BMW");
        System.out.println(bmw.getName());
        System.out.println(SimpleFactory.getBenz().getName());
    }

    /**
     * 创建对象实例的功能，无须关心其具体实现
     * 被创建实例的类型可以是接口、抽象类，也可以是具体的类
     */
    public static Car getCar(String name) {
        if (name.equals("Benz")) {
            return new Benz();
        } else if (name.equals("BMW")) {
            return new BMW();
        } else {
            return null;
        }
    }

    /**
     * 多方法工厂，常用
     * 多方法的工厂模式为不同产品，提供不同的生产方法，使用时 需要哪种产品就调用该种产品的方法，使用方便、容错率高
     */
    public static Car getBenz() {
        return new Benz();
    }


}
