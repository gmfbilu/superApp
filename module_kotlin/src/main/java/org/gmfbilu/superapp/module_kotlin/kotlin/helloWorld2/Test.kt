package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld2

fun main(args: Array<String>) {
    //高阶函数，就是相当于帮我们实现了某些方法
    val maxBy = 非诚勿扰数据库.maxBy { it.age }
    非诚勿扰数据库.minBy { it.height }
    val filter = 非诚勿扰数据库.filter {
        (it.age > 18) and (it.height > 168)
    }

}