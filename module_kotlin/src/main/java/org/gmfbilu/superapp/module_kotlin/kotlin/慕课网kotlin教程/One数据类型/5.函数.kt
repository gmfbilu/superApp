package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型

fun method1(): Unit {//kotlin中的Unit相当于Java中的void，可以省略
}

/**
 * 返回值为String，当某个变量的值可以为 null 的时候，必须在声明处的类型后添加 ? 来标识该引用可为空
 * 调用的时候method2(0)直接返回null,method2(0)!!.length报空指针异常
 *
 */
fun method2(obj: Any): String? {
    if (obj is String) {
        return obj
    }
    return null
}


//函数的参数可以有默认参数。调用的时候有默认参数的可以不写，method3(2)和method3(1,2)都可以。优先声明带有默认参数的函数而不是声明重载函数
fun method3(a: Int, b: Int = 0) {
}

//匿名函数，使用一个变量接收这个函数的时候是不需要函数名的，使用的时候age(8)
var age = fun(a: Int): Int {
    return a
}

//将表达式作为函数体、返回值类型自动推断的函数
fun sum(a: Int, b: Int) = a + b

//扩展方法就是对一个类添加额外的方法，而不用写在类里面，this代表那个类。使用的时候直接使用类实例访问。为了尽量减少 API 污染，尽可能地限制扩展函数的可见性
//如果一个类定义有一个成员函数与一个扩展函数，而这两个函数又有相同的接收者类型、 相同的名字，并且都适用给定的参数，这种情况总是取成员函数
fun StringBuffer.add(str: String): StringBuffer = this.append(str)
//为了在接收者类型表达式中使用泛型，我们要在函数名前声明泛型参数
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // “this”对应该列表
    this[index1] = this[index2]
    this[index2] = tmp
}
//可以为可空的接收者类型定义扩展,这样的扩展可以在对象变量上调用， 即使其值为 null
fun Any?.toString(): String {
    if (this == null) return "null"
    // 空检测之后，“this”会自动转换为非空类型，所以下面的 toString()
    // 解析为 Any 类的成员函数
    return toString()
}


//单表达式函数
fun theAnswer() = 42

//泛型函数
fun <T> singletonList(item: T): List<T>? {
   return null
}
// 泛型扩展函数
fun <T> T.basicToString(): String? {
    //要调用泛型函数，在调用处函数名之后指定类型参数即可
    val l = singletonList<Int>(1)
    //可以省略能够从上下文中推断出来的类型参数，所以以下示例同样适用
    val l1 = singletonList(1)
   return null
}
