package org.gmfbilu.superapp.module_kotlin.helloWorld


class Girl(var chactor: String, var voice: String) {

    fun cry() {
        println("Girl cry")
    }
}

fun main(args: Array<String>) {
    //创建对象
    var girl = Girl("彪悍", "甜美")
    girl.cry()
}