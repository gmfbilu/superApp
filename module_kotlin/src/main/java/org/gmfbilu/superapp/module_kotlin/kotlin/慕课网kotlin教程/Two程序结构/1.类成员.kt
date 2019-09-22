package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.Two程序结构


/**
 * 主構造方法中val和var修飾的都是屬性，沒有被修飾的只是構造方法參數。属性可以直接使用
 *
 */
class 类成员(val a: Int, b: String) {

    //out和Java中的extend类似,但是String是final类型的
    fun test(args: Array<out String>) {
        类成员.test(a.toString())
        //类成员.test(b)
        类成员.LOAN_TITLE
    }

    //成員變量，自動推斷爲Int，屬性
    var c = 0
    //成員變量，屬性
    val d: Int = 0


    //构造方法的方法体，每次调用鼓噪方法前先调用init代码块
    init {

    }

    //一个kotlin只有一个 companion object {}结构，类似java的静态。称为伴生对象，直接可以使用类名调用，用来定义静态变量，静态常量，静态方法
    companion object {
        //kotlin的main函数
        @JvmStatic
        fun main(args: Array<String>) {

        }

        //常量，
        val LOAN_TYPE = "loanType"
        val LOAN_TITLE = "loanTitle"

        fun test(str: String) {

        }
    }

}