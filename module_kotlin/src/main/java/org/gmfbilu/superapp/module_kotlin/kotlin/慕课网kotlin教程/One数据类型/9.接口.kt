package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型


/**
 * 接口的方法默认都是open的，这点和类和抽象类不一样
 * 接口里面的方法既可以有抽象方法也可以有方法实现
 *
 * 口中声明的属性要么是抽象的，要么提供访问器的实现
 * 属性默认也是open的
 */
interface I {

    val prop: Int // 抽象的

    val propertyWithImplementation: String //提供访问器的实现
        get() = "foo"


    fun a()
    fun b(a: Int): String {
        print(prop)
        return "$a"
    }
}

/**
 * 实现接口
 * 需要重写接口里面的抽象方法和抽象属性
 */
class IImpl : I {
    override val prop: Int
        get() = 0

    override fun a() {
        println(propertyWithImplementation)
    }

}

/**
 * 抽象类实现接口就不需要实现接口里面的抽象方法和抽象属性
 */
abstract class AImpl : I {

}

/**
 * 接口继承接口
 */
interface N : I {

    val firstName: Int
    val lastName: Int

    override val prop: Int
        get() = firstName + lastName
}