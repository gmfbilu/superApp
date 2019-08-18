package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型


fun main(args: Array<String>) {
    println(helloTrump)
    println("$countries+$cities=${countries + cities}")
}


//====================================================================数据类型
//val是运行时常量，类似Java中的final，在编译期不可知道具体的值，不可能变。后面可以省略数据类型，叫类型推导
//const val是编译期常量，在编译期可知道具体的值
//var是变量
//boolean类型
val isMe: Boolean = true
val isWeek = true
//Number类型，kotlin里面的Int类型是Java里面的int类型和Integer类型的合体。kotlin不区分装箱类型和非装箱类型
val countries: Int = 221
val cities: Double = 0.0
val suns: Long = 10
val distances: Float = 0.0f
val shchools: Short = 100
val families: Byte = 4
val language: Char = '\n' //换行符
//字符串，kotlin里面的双等号(==)和Java里面的equals完全等价，kotlin里面的三等号(===)和Java里面的双等号完全等价
val hello: String = "helloWorld"
val helloTrump: String = "hello\"trump\""
//mayNull这个可能为null
val mayNull: String? = "null"
//字符串模板,println("$countries+$cities=${countries + cities}")
//区间
val range: IntRange = 0..1042 //[0,1024]
val range_exclusive: IntRange = 0 until 1024 //(0,1024]
//编译期常量，在编译的时候就可以确定的。 val myName="batman" 也是常量，但是不是编译期常量，可以通过反射修改
const val myName = "batman" //等价于Java中的public final String myName="batman"


//========================================================================数组
val bookList: IntArray = intArrayOf(0, 2, 8, 1)
val nameList: CharArray = charArrayOf('1', '2')
val cityList: Array<String> = arrayOf("香港", "拉萨")
val girlList: Array<妹子> = arrayOf(妹子("", ""))
//循环遍历
fun getSingle(array: Array<妹子>) {

    for (book in bookList) {
        println(book)
    }

    bookList.forEach {
        println(it)
    }

    //这里的return会结束整个getSingle函数
    bookList.forEach {
        if (it.equals("a")) {
            return
        }
        println(it)
    }

    //这里的return只会结束掉此循环
    bookList.forEach ForEach@{
        if (it.equals("a")) {
            return@ForEach
        }
        println(it)
    }
}


//==============================================================================类
//类，直接指定构造方法。kotlin中的所有类是Any类的子类
class 妹子(val 性格: String, val 声音: String) {
    //相当于java中的构造代码块
    init {
        println("性格+$性格")
    }
}

//创建对象
val 我喜欢的妹子: 妹子 = 妹子("甜美", "漂亮")

open class 人(val 性格: String, val 声音: String) {
    init {
        println("new了一个${this.javaClass.simpleName},声音+$声音")
    }
}

//继承
class 帅哥(性格: String, 声音: String) : 人(性格, 声音)


//=======================================================================================函数
//kotlin中的Unit相当于Java中的void，可以省略
fun method1(): Unit {

}


//返回值为String
fun method2(): String {
    return ""
}

//有参数
fun method3(a: Int, b: Int) {

}

//匿名函数，使用一个变量接收这个函数的时候是不需要函数名的，使用的时候age(8)
var age = fun(a: Int): Int {
    return a
}

//这种返回值不可能为null
fun getName(): String {
    return ""
}

//这种返回值可能为null，println(getNameS()?.length) 直接返回null， println(getNameS()!!.length)就和Java一样报空指针异常
fun getNameS(): String? {
    return null
}


//============================================================lambda表达式
//lambda表达式就是匿名函数
// 写法：有参数：{参数列表-> 函数体，最后一行是返回值}。无参数：{函数体，最后一行是返回值}
//lambda表达式的类型:()->Unit表示无参，返回值为Unit.(Int)->Int表示传入一个整数，返回一个整数。(String,(String)->String)->Boolean表示传入一个String和lambda表达式，返回一个String
//lambda表达式的调用：用()调用，等价于invoke()
//lambda表达式的简化：函数参数调用时最后一个lambda可以移出去；函数参数只有一个lambda时，调用时小括号可以省略；lambda参数只有一个参数可默认为it;
val sum = { a: Int, b: Int -> a + b } //使用： sum(1,1)或 sum()或sum都可以，sum(1,1)等价于sum.invoke(1,1)
val printhello = { print("say") } //使用：printhello()或printhello

