package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型

import java.util.*

fun main() {


    //使用命名参数我们可以使代码更具有可读性：
    reformat(str = "",
            normalizeCase = true,
            upperCaseFirstLetter = true,
            divideByCamelHumps = false,
            wordSeparator = '_'
    )
    //并且如果我们不需要所有的参数：
    reformat(str = "", wordSeparator = '_')

    //允许将可变数量的参数传递给函数：
    val list = asList(1, 2, 3)
    //如果我们已经有一个数组并希望将其内容传给该函数，我们使用伸展（spread）操作符（在数组前面加 *）：
    val a = arrayOf(1, 2, 3)
    val list1 = asList(-1, 0, *a, 4)

    // 用中缀表示法调用该函数
    1 method14 2
    // 等同于这样
    1.method14(2)


}

//===============================================================一般函数(成员函数)
//无返回值无参数
fun method1(): Unit {//kotlin中的Unit相当于Java中的void，可以省略
}

/**
 * 返回值为String，当某个变量的值可以为 null 的时候，必须在声明处的类型后添加 ? 来标识该引用可为空
 * 调用的时候method2(0)直接返回null,method2(0)!!.length报空指针异常
 */
fun method2(obj: Any): String? {
    if (obj is String) {
        return obj
    }
    return null
}


//================================================================形参带有默认值的函数
//函数的参数可以有默认参数。调用的时候有默认参数的可以不写，method3(2)和method3(1,2)都可以。优先声明带有默认参数的函数而不是声明重载函数
fun method3(a: Int, b: Int = 0) {
    method3(2)
    method3(1, 2)
}


//如果一个默认参数在一个无默认值的参数之前，那么该默认值只能通过使用命名参数调用该函数来使用：method12(baz = 1) // 使用默认值 bar = 0
fun method12(bar: Int = 0, baz: Int) {
    method12(baz = 1)
}

//如果在默认参数之后的最后一个参数是 lambda 表达式，那么它既可以作为命名参数在括号内传入，也可以在括号外传入：
fun method13(bar: Int = 0, baz: Int = 1, qux: () -> Unit) {
    method13(1) { println("hello") }     // 使用默认值 baz = 1
    method13(qux = { println("hello") }) // 使用两个默认值 bar = 0 与 baz = 1
    method13 { println("hello") }        // 使用两个默认值 bar = 0 与 baz = 1
}


//================================================================匿名函数
//使用一个变量接收这个函数的时候是不需要函数名的，使用的时候method4(8)
var method4 = fun(a: Int): Int {
    return a
}


//================================================================将表达式作为函数体的函数
//将表达式作为函数体、返回值类型自动推断的函数
fun method5(a: Int, b: Int) = a + b


//单表达式函数
fun method9() = 42



//================================================================扩展方法函数
//扩展方法就是对一个类添加额外的方法，而不用写在类里面，this代表那个类。使用的时候直接使用类实例访问。为了尽量减少 API 污染，尽可能地限制扩展函数的可见性
//如果一个类定义有一个成员函数与一个扩展函数，而这两个函数又有相同的接收者类型、 相同的名字，并且都适用给定的参数，这种情况总是取成员函数
fun StringBuffer.method6(str: String): StringBuffer = this.append(str)


//================================================================泛型函数
fun <T> method10(item: T): List<T>? {
    return null
}


// 泛型扩展函数
fun <T> T.method11(): String? {
    //要调用泛型函数，在调用处函数名之后指定类型参数即可
    val l = Collections.singletonList<Int>(1)
    //可以省略能够从上下文中推断出来的类型参数，所以以下示例同样适用
    val l1 = Collections.singletonList(1)
    return null
}

//为了在接收者类型表达式中使用泛型，我们要在函数名前声明泛型参数
fun <T> MutableList<T>.method7(index1: Int, index2: Int) {
    val tmp = this[index1] // “this”对应该列表
    this[index1] = this[index2]
    this[index2] = tmp
}

//可以为可空的接收者类型定义扩展,这样的扩展可以在对象变量上调用， 即使其值为 null
fun Any?.method8(): String {
    if (this == null) return "null"
    // 空检测之后，“this”会自动转换为非空类型，所以下面的 toString()
    // 解析为 Any 类的成员函数
    return toString()
}



//================================================================命名参数函数
//可以在调用函数时使用命名的函数参数。当一个函数有大量的参数或默认参数时这会非常方便
//对于 JVM 平台：在调用 Java 函数时不能使用命名参数语法，因为 Java 字节码并不总是保留函数参数的名称。
fun reformat(str: String,
             normalizeCase: Boolean = true,
             upperCaseFirstLetter: Boolean = true,
             divideByCamelHumps: Boolean = false,
             wordSeparator: Char = ' ') {
}


//================================================================可变数量的参数（Varargs）函数
//函数的参数（通常是最后一个）可以用 vararg 修饰符标记：
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}



//================================================================中缀表示法函数
//标有 infix 关键字的函数也可以使用中缀表示法（忽略该调用的点与圆括号）调用。中缀函数必须满足以下要求：
//它们必须是成员函数或扩展函数；
//它们必须只有一个参数；
//其参数不得接受可变数量的参数且不能有默认值。
infix fun Int.method14(x: Int): Int {
    return x
}
//中缀函数调用的优先级低于算术操作符、类型转换以及 rangeTo 操作符。 以下表达式是等价的：
//1 method14 2 + 3 与 1 method14 (2 + 3)
//0 until n * 2 与 0 until (n * 2)
//xs union ys as Set<*> 与 xs union (ys as Set<*>)
//中缀函数调用的优先级高于布尔操作符 && 与 ||、is- 与 in- 检测以及其他一些操作符。这些表达式也是等价的：
//a && b xor c 与 a && (b xor c)
//a xor b in c 与 (a xor b) in c
//中缀函数总是要求指定接收者与参数。当使用中缀表示法在当前接收者上调用方法时，需要显式使用 this；不能像常规方法调用那样省略。这是确保非模糊解析所必需的
class MyStringCollection {
    infix fun add(s: String) { /*……*/ }

    fun build() {
        this add "abc"   // 正确
        add("abc")       // 正确
        //add "abc"        // 错误：必须指定接收者
    }
}




//================================================================局部函数
//局部函数,Kotlin 支持局部函数，即一个函数在另一个函数内部：
/*fun dfs(graph: Grop) {
    fun dfs(current: Vertex, visited: MutableSet<Vertex>) {
        if (!visited.add(current)) return
        for (v in current.neighbors)
            dfs(v, visited)
    }

    dfs(graph.vertices[0], HashSet())
}*/
//局部函数可以访问外部函数（即闭包）的局部变量，所以在上例中，visited 可以是局部变量：
/*fun dfs(graph: Graph) {
    val visited = HashSet<Vertex>()
    fun dfs(current: Vertex) {
        if (!visited.add(current)) return
        for (v in current.neighbors)
            dfs(v)
    }

    dfs(graph.vertices[0])
}*/



//================================================================内联函数


//================================================================尾递归函数,
//Kotlin 支持一种称为尾递归的函数式编程风格。 这允许一些通常用循环写的算法改用递归函数来写，而无堆栈溢出的风险。 当一个函数用 tailrec 修饰符标记并满足所需的形式时，编译器会优化该递归，留下一个快速而高效的基于循环的版本：
//这段代码计算余弦的不动点（fixpoint of cosine），这是一个数学常数。 它只是重复地从 1.0 开始调用 Math.cos，直到结果不再改变，对于这里指定的 eps 精度会产生 0.7390851332151611 的结果
val eps = 1E-10 // "good enough", could be 10^-15
tailrec fun findFixPoint(x: Double = 1.0): Double = if (Math.abs(x - Math.cos(x)) < eps) x else findFixPoint(Math.cos(x))
//最终代码相当于这种更传统风格的代码：
private fun findFixPoint(): Double {
    var x = 1.0
    while (true) {
        val y = Math.cos(x)
        if (Math.abs(x - y) < eps) return x
        x = Math.cos(x)
    }
}
//要符合 tailrec 修饰符的条件的话，函数必须将其自身调用作为它执行的最后一个操作。在递归调用后有更多代码时，不能使用尾递归，并且不能用在 try/catch/finally 块中


//================================================================高阶函数和 Lambda 表达式
/**
 * Kotlin 函数都是头等的，这意味着它们可以存储在变量与数据结构中、作为参数传递给其他高阶函数以及从其他高阶函数返回。可以像操作任何其他非函数值一样操作函数
 * 为促成这点，作为一门静态类型编程语言的 Kotlin 使用一系列函数类型来表示函数并提供一组特定的语言结构，例如 lambda 表达式
 *
 */
//================================================================高阶函数
/**
 *
 */





