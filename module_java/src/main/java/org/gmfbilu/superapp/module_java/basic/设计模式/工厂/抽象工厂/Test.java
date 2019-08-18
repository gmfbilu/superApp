package org.gmfbilu.superapp.module_java.basic.设计模式.工厂.抽象工厂;

public class Test {

    public static void main(String[] args) {
        DefaultFactory defaultFactory = new DefaultFactory();
        System.out.println(defaultFactory.getCar("BMW").getName());
/*        String tem  = "hello";
        String s = tem+"world";
        String s1 = "helloworld";
        System.out.println(s==s1);*/

       // String s1 = "ja";
        //String s2 = "va";
        String s3 = "java";
        String s4 = "ja" + "va";
        System.out.println(s3 == s4);
    }
}
