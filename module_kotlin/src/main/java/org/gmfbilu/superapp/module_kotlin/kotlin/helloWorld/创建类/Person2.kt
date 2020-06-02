package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.创建类

//data class 是数据类
data class Person3(var name: String, var age: Int? = null) //age是可空类型，null是age的默认值，有默认值的参数在使用的时候可以省略


//顶层函数
fun main(args: Array<String>) {
    val persons = listOf(Person3("batman"),//省略了年龄，此人的年龄就是默认值null
            Person3("", 2),
            Person3("joker", null),
            Person3("trump", age = 10)) //命名参数

    val oldest = persons.maxBy { it.age ?: 0 }//lambda表达式，Elvis运算符。如果age是null,Elvis运算符(?:)会返回0
    println("the oldest person is:$oldest")//字符串模板
    //the oldest person is:Person3(name=trump,age=10),自动生成toString方法





}