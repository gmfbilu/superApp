package org.gmfbilu.superapp.module_java.basic.反射;

import android.util.Log;

public class Cat {

    public static final String TAG = Cat.class.getSimpleName();

    private String name;
    @Deprecated
    public int age;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void eat(String food) {
        Log.d(TAG, "eat food " + food);
    }

    public void eat(String... foods) {
        StringBuilder s = new StringBuilder();
        for (String food : foods) {
            s.append(food);
            s.append(" ");
        }
        Log.d(TAG, "eat food " + s.toString());
    }

    public void sleep() {
        Log.d(TAG, "sleep");
    }

    @Override
    public String toString() {
        return "name = " + name + " age = " + age;
    }
}
