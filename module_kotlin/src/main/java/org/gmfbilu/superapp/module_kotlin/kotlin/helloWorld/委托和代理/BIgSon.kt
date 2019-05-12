package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.委托和代理

/**
 * object表示此类实例在内存有且仅有一个
 * 每次BIgSon()都相当于创建一个对象
 */
object BIgSon : IWashBowl {
    override fun washing() {
        println("我是大头儿子，每次洗碗赚一块钱")
    }


}