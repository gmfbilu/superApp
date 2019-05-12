package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.代码块

class Kuai1 {

    /**
     * 构造代码块
     */
    var number = 0

    init {
        number = 1
    }


    /**
     * 静态代码块
     */
    companion object {

        var n = 0

        init {
            n = 1
        }
    }

    /**
     * 方法代码块
     */
    fun test() {
        run {
            var a = 1
        }
    }
}