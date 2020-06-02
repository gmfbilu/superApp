package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.函数

import org.gmfbilu.superapp.lib_base.utils.ToastUtil
import org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型.User

fun main(args:Array<String>){

    //创建一个set集合
    val hashSetOf = hashSetOf(1, 2, 3)
    val arrayListOf = arrayListOf(1, 2, 2)
    val hashMapOf = hashMapOf(1 to "one", 2 to "two")//to是一个特殊的函数调用，称为中缀调用。1 to "one"等价于1.to("one")
    val pair = 28 to "age"
    val (number,age) = 28 to "age"
    println(hashMapOf.javaClass)//class java.util.HashMap，等价于java的getClass()
    println(arrayListOf.last())//获取最后一个元素
    println(arrayListOf.max())//得到最大值

    joinToString(hashSetOf,"","","")
    //可以显式的标明一些参数的名称。当重命名参数的时候需要使用idea来重命名。当调用Java函数或者Android函数的时候不能采用命名参数
    joinToString(hashSetOf,separator = "",prefix = "",postfix = "")
    //可以省略默认参数
    joinToString(hashSetOf,postfix = "")

    //调用扩展函数，String是接收者类型，“kotlin”是接收者对象
    println("kotlin".lastChar())

}


fun <T> joinToString(Collection:Collection<T>,separator:String="",prefix:String="",postfix:String){

}

//扩展函数，String是接收者类型，this是接收者对象。接收者类型是由扩展函数定义的，接收者对象是该类型的一个实例
//this也可以省略
fun String.lastChar():Char=this.get(this.length-1)

fun saveUser(user:Lion){
    if (user.name.isEmpty()){
        return
    }
    if (user.id.isEmpty()){
        return
    }
    println(user)
}


//局部函数
fun saveUser2(user:Lion){
    fun validate(value:String,fieldName:String){
        if (value.isEmpty()){
            ToastUtil.show("$fieldName 不能为空")
            return
        }
    }
    validate(user.name,"姓名")
    validate(user.id,"Id")
    println(user)
}

data class Lion(val id:String,val name:String)

fun Lion.validateBeforeSave(){
    fun validate(value:String,fieldName:String){
        if (value.isEmpty()){
            ToastUtil.show("$fieldName 不能为空")
            return
        }
    }
}


//单例
object Single{

}

val sum={x:Int,y:Int ->
    println("a is a")
    x+y}