package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.设计模式.单例

/**
 * 懒汉式线程安全,效率低.单例一直没用过，但是这个东西还是一直占用我的内存
 */
class Singleton2 private constructor() {

    companion object {
        private var instance: Singleton2? = null

        @Synchronized
        fun get(): Singleton2 {
            if (instance == null) {
                instance = Singleton2()
            }
            return instance!!
        }
    }
}