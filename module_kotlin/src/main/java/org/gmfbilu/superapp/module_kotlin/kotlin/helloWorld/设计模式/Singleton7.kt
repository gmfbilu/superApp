package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.设计模式

/**
 * 双重校验锁,避免在多线程环境中被创建多次
 */
class Singleton7 private constructor() {

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Singleton7()
        }
    }
}