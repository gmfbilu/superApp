package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.设计模式

/**
 * 静态内部类：推荐
 */
class Singleton5 private constructor(){

    companion object{
        fun getInstance()=Holder.instance
    }

    private object Holder{
        val instance = Singleton5()
    }
}