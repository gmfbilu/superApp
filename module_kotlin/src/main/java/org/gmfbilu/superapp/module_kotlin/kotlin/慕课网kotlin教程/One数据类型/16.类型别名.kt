package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型

import android.net.Network
import java.io.File

/**
 * 类型别名为现有类型提供替代名称。 如果类型名称太长，你可以另外引入较短的名称，并使用新的名称替代原类型名
 *
 */

//它有助于缩短较长的泛型类型。 例如，通常缩减集合类型是很有吸引力的：
typealias NodeSet = Set<Network>
typealias FileTable<K> = MutableMap<K, MutableList<File>>

//你可以为函数类型提供另外的别名：
typealias MyHandler = (Int, String, Any) -> Unit
typealias Predicate<T> = (T) -> Boolean

//你可以为内部类和嵌套类创建新名称：
class A1 {
    inner class Inner
}
typealias AInner = A1.Inner



//类型别名不会引入新类型。 它们等效于相应的底层类型。 当你在代码中添加 typealias Predicate<T> 并使用 Predicate<Int> 时，Kotlin 编译器总是把它扩展为 (Int) -> Boolean。 因此，当你需要泛型函数类型时，你可以传递该类型的变量，反之亦然
typealias Predicate1<T> = (T) -> Boolean
fun foo(p: Predicate1<Int>) = p(42)

fun main() {
    val f: (Int) -> Boolean = { it > 0 }
    println(foo(f)) // 输出 "true"

    val p: Predicate1<Int> = { it > 0 }
    println(listOf(1, -2).filter(p)) // 输出 "[1]"
}