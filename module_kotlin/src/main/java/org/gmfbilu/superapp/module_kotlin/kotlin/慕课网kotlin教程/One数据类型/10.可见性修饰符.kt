package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型

/**
 * 类、对象、接口、构造函数、方法、属性和它们的 setter 都可以有 可见性修饰符
 * getter 总是与属性有着相同的可见性
 * Kotlin 中有这四个可见性修饰符：private、 protected、 internal 和 public。 默认可见性是 public
 *
 * 如果你不指定任何可见性修饰符，默认为 public，这意味着你的声明将随处可见
 * 如果你声明为 private，它只会在声明它的文件内可见
 * 如果你声明为 internal，它会在相同模块内随处可见
 *
 * 类和接口:
 * 对于类内部声明的成员：
 * private 意味着只在这个类内部（包含其所有成员）可见
 * protected—— 在子类中可见。
 * internal —— 能见到类声明的 本模块内 的任何客户端都可见其 internal 成员
 * public —— 将随处可见
 * Kotlin 中，外部类不能访问内部类的 private 成员
 * 如果你覆盖一个 protected 成员并且没有显式指定其可见性，该成员还会是 protected 可见性
 */

private fun foo() { } // 在10.可见修饰符.kt内可见

public var bar: Int = 5 // 该属性随处可见
    private set         // setter 只在10.可见修饰符.kt 内可见

internal val baz = 6    // 相同模块内可见

/**
 * 要指定一个类的的主构造函数的可见性，使用以下语法
 * 这里的构造函数是私有的。默认情况下，所有构造函数都是 public
 */
class C private constructor(a: Int) {  }