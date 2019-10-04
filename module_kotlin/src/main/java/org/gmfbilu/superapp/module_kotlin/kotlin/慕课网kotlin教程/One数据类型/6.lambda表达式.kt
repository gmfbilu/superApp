package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型

//lambda表达式就是匿名函数
// 写法：有参数：{参数列表-> 函数体，最后一行是返回值}。无参数：{函数体，最后一行是返回值}
//lambda表达式的类型:()->Unit表示无参，返回值为Unit.(Int)->Int表示传入一个整数，返回一个整数。(String,(String)->String)->Boolean表示传入一个String和lambda表达式，返回一个String
//lambda表达式的调用：用()调用，等价于invoke()
//lambda表达式的简化：函数参数调用时最后一个lambda可以移出去；函数参数只有一个lambda时，调用时小括号可以省略；lambda参数只有一个参数可默认为it;
val sum = { a: Int, b: Int -> a + b } //使用： sum(1,1)或 sum()或sum都可以，sum(1,1)等价于sum.invoke(1,1)
val sum1 = fun(a: Int, b: Int): Int {
    return a + b
}

fun sum2(a: Int, b: Int): Int = a + b
//上面三種函數寫法是等價的
val printhello = { print("say") } //使用：printhello()或printhello
