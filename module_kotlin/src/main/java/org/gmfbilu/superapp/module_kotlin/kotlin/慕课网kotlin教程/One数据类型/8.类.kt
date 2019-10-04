package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型

import androidx.constraintlayout.solver.widgets.Rectangle

//============================================================对象
val rectangle = Rectangle() //实例的创建，没有Java的new关键字
val 我喜欢的妹子: Meizi = Meizi("甜美", "漂亮")


//===============================================================类

interface Man {
    //接口成员默认就是“open”的
    fun eat() {

    }
}

abstract class Chimpanzee : People() {

    //可以用一个抽象成员覆盖一个非抽象的开放成员
    override abstract fun eat()
}

open class People {
    open val vertexCount: Int = 0
    open fun peopleMethod() {}
    open fun eat() {

    }
}

/**
 * 类，直接指定构造方法.一个类可以有一个主构造函数以及一个或多个次构造函数。主构造函数是类头的一部分。如果主构造函数没有任何注解或者可见性修饰符，可以省略这个 constructor 关键字。
 * 主构造的参数可以在初始化块中使用。它们也可以在类体内声明的属性初始化器中使用
 * 非抽象类没有声明任何（主或次）构造函数，它会有一个生成的不带参数的主构造函数。构造函数的可见性是 public。如果你不希望你的类有一个公有构造函数，你需要声明一个带有非默认可见性的空的主构造函数
 * class DontCreateMe private constructor () { /*……*/ }
 *
 * 会为Meizi类提供以下功能：
 * 1.所有属性的 getters （对于 var 定义的还有 setters）
 * 2.equals()
 * 3.hashCode()
 * 4.toString()
 * 5.copy()
 * 6.所有属性的 component1()、 component2()……等等
 *
 * 继承的父类必须是open修饰，要被重写的方法也要被open修饰,
 * 被重写的成员变量也必须要要被open修饰，可以用一个 var 属性覆盖一个 val 属性，但反之则不行。也可以主构造方法中使用 override 关键字来重写父类成员变量
 * 基类构造函数执行时，子类中声明或覆盖的属性都还没有初始化。设计一个父类时，应该避免在构造函数、属性初始化器以及 init 块中使用 open 成员
 *
 * 如果一个类从它的直接超类继承相同成员的多个实现,它必须覆盖这个成员并提供其自己的实现。为了表示采用从哪个超类型继承的实现，我们使用由尖括号中超类型名限定的 super
 */
class Meizi(val 性格: String, val 声音: String) : People(), Man {

    //相当于java中的构造代码块，所有初始化块中的代码都会在次构造函数体之前执行
    init {
        println("性格+$性格")
    }

    //次构造函数,
    constructor(meizi: Meizi) : this("", "") {

    }

    fun a() {}
    fun b() {}

    override fun peopleMethod() {
        super.peopleMethod()
    }

    override val vertexCount: Int
        get() = super.vertexCount

    inner class AmerMeizi {
        //在一个内部类中访问外部类的超类，可以通过由外部类名限定的 super 关键字来实现：super@Outer
        fun drawAndFill() {
            super@Meizi.peopleMethod() // 调用 People 的 peopleMethod() 实现
        }
    }

    // 编译器要求覆盖 eat()：可以同时继承 Man 与 People， 但是二者都有各自的 eat() 实现，所以我们必须在 Meizi 中覆盖 eat()， 并提供其自身的实现以消除歧义
    override fun eat() {
        super<Man>.eat()// 调用 Man.eat()
        super<People>.eat()// 调用 People.eat()
    }

    //延迟初始化属性与变量,要检测一个 lateinit var 是否已经初始化过，请在该属性的引用上使用 .isInitialized
    lateinit var subject: People


}


//=======================================================================伴生对象的扩展
//如果一个类定义有一个伴生对象 ，你也可以为伴生对象定义扩展函数与属性
class MyClass {
    companion object { }  // 将被称为 "Companion"
}

fun MyClass.Companion.printCompanion() { println("companion") }

fun main() {
    MyClass.printCompanion()
}


//============================================================================嵌套类
//类可以嵌套在其他类中：相当于Java中的内部类
class Outer {
    private val bar: Int = 1
    class Nested {
        fun foo() = 2
    }
}
val demo = Outer.Nested().foo() // == 2


//===========================================================================内部类
//类可以标记为 inner 以便能够访问外部类的成员。内部类会带有一个对外部类的对象的引用
class Outer1 {
    private val bar: Int = 1
    inner class Inner {
        fun foo() = bar
    }
}

val demo1 = Outer1().Inner().foo() // == 1

//============================================================================匿名内部类
/*window.addMouseListener(object : MouseAdapter() {

    override fun mouseClicked(e: MouseEvent) { …… }

    override fun mouseEntered(e: MouseEvent) { …… }
})*/
