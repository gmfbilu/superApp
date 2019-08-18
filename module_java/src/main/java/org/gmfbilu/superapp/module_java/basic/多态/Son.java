package org.gmfbilu.superapp.module_java.basic.多态;

public class Son extends Father {

    public String name = "son";
    public String age = "10";

    public void test() {
        System.out.println("Son test");
    }

    public void sonUnik() {
        System.out.println("Son sonUnik");
    }

    public <T> void fer(T t) {

    }

    public <T extends Number> void fer2(T t) {

    }

    public static void main(String args[]) throws Exception {
        Father father = new Son();
        father.test();
        ((Son) father).sonUnik();
        System.out.println(father.name);
        System.out.println(((Son) father).name);
        System.out.println(((Son) father).age);
        ((Son) father).fer("haha");
    }

}
