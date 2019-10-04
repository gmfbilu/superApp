package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型

//=======================================================================比较
var witch = setterVisibility == helloTrump //双等号(==)和Java里面的equals完全等价
var witchHunt = setterVisibility === helloTrump // 三等号(===)和Java里面的双等号完全等价


fun ifMathod(a: Int, b: Int): Int {
    if (a > b) {
        return a
    } else {
        return b
    }
}

fun ifMathod1(a: Int, b: Int) = if (a > b) a else b //类似Java中的三目运算符

fun ifMathod2(param: Int) {
    val result = if (param == 1) {
        "one"
    } else if (param == 2) {
        "two"
    } else {
        "three"
    }
}

//when类似Java中的switch，when有返回值更强大
fun whenMethod(str: String): Int {
    return when (str) {
        "1" -> 1
        "2" -> 2
        else -> 3
    }

}

fun whenMethod1() {
    when {
        //检测元素是否存在于集合中
        "orange" in items -> println("juicy")
        "apple" in items -> println("apple is fine too")
    }


    val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
    fruits.filter { it.startsWith("a") }//过滤
            .sortedBy { it } //排序
            .map { it.toUpperCase() }//映射
            .forEach { println(it) } //遍历
}

fun whenMethod2(color: String): Int = when (color) {
    "Red" -> 0
    "Green" -> 1
    "Blue" -> 2
    else -> throw IllegalArgumentException("Invalid color param value")
}

fun notNUll(str: String?) {
    println(str?.length) //If not null 缩写
    println(str?.length ?: "empty")//If not null and else 缩写
    str?.let {
        // 代码会执行到此处, 假如str不为null
    }
    val mapped = str?.let { "代码会执行到此处, 假如str不为null" } ?: "defaultValueIfValueIsNull"
}

fun tryCatch() {
    val result = try {
        ""
    } catch (e: ArithmeticException) {
        throw IllegalStateException(e)
    }
    // 使用 result
}

fun withMethod() {
    val meizi = Meizi("", "")
    with(meizi) {
        //对一个对象实例调用多个方法
        a()
        b()
    }
}

