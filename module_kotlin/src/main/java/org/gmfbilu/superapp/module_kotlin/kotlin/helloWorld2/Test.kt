package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld2

fun main(args: Array<String>) {
    //高阶函数，就是相当于帮我们实现了某些方法，参数为函数或者返回值为函数的函数
    val maxBy = 非诚勿扰数据库.maxBy { it.age }
    val minBy = 非诚勿扰数据库.minBy { it.height }
    val filter = 非诚勿扰数据库.filter {
        (it.age > 18) and (it.height > 168)
    }
    val map = 非诚勿扰数据库.map { "${it.name}:${it.age}" }
    val any = 非诚勿扰数据库.any { it.age == 0 }
    val count = 非诚勿扰数据库.count { it.age < 20 }
    val find = 非诚勿扰数据库.find { it.address == "usa" }

}