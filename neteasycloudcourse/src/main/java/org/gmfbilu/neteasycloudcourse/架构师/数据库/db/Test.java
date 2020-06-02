package org.gmfbilu.neteasycloudcourse.架构师.数据库.db;

public class Test {

    public int age;
    public String name;
    public double price;

    public Test(int age, String name,double price) {
        this.age = age;
        this.name = name;
        this.price=price;
    }

    public Test() {
    }

    @Override
    public String toString() {
        return "Test{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
