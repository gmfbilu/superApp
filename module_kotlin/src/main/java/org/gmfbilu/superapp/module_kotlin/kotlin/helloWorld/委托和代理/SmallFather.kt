package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.委托和代理

/**
 * by关键字相当于将washing()方法交由BIgSon处理，当然自己也可以重写
 */
class SmallFather : IWashBowl by BIgSon {
    override fun washing() {
        println("小头爸爸在洗碗，每次赚十块钱")
        BIgSon.washing()
    }
}