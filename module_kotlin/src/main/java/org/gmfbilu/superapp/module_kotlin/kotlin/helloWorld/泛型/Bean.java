package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.泛型;

public class Bean<T extends String> {

    T data;
    public Bean(T t) {
        this.data = t;
    }
}
//Bean<String> bean = new Bean<>("666666");