package org.gmfbilu.superapp.module_kotlin.helloWorld


/**
 * 继承
 */
class Son : Father() {

    override fun action() {
        super.action()
    }
}

fun main(args: Array<String>) {
    //创建无参对象
    var son = Son()
    son.action()
}