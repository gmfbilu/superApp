package org.gmfbilu.superapp.module_kotlin.helloWorld.代码块;

public class Kuai {


    /**
     * 构造代码块
     */
    int number;

    {
        number = 1;
    }

    /**
     * 静态代码块
     */
    static int n;

    static {
        n = 1;
    }

    /**
     * 方法代码块
     */
    void test() {
        {
            int a = 1;
        }
    }
}
