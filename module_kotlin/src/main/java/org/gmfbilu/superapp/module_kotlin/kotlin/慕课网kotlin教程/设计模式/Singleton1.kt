package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.设计模式

/**
 * 懒汉式非线程安全
 */
class Singleton1 private constructor() {

    companion object {
        //LazyThreadSafetyMode.NONE初始化非线程安全
        val instance by lazy(LazyThreadSafetyMode.NONE) {
            Singleton1()
        }
    }
}