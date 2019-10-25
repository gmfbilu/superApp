package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * 委托模式已经证明是实现继承的一个很好的替代方式， 而 Kotlin 可以零样板代码地原生支持它
 */


fun main() {
    val b = BaseImpl(10)
    val derived = Derived(b)
    println(derived.message)
    Derived(b).printMessage()//会输出“abc”而不是“10”
    Derived(b).printMessageLine()

    println(lazyValue) //computed!Hello
    println(lazyValue) //Hello

    val user = User1()
    user.name = "first"
    user.name = "second"

    //委托属性会从这个映射中取值（通过字符串键——属性的名称）：
    println(user2.name) // Prints "John Doe"
    println(user2.age)  // Prints 25
}


interface Base {
    val message: String
    fun printMessage()
    fun printMessageLine()
}

class BaseImpl(val x: Int) : Base {
    override val message = "BaseImpl: x = $x"
    override fun printMessage() {
        print(x)
    }

    override fun printMessageLine() {
        println(message)
    }
}

//Derived 类可以通过将其所有公有成员都委托给指定对象来实现一个接口 Base：
//Derived 的超类型列表中的 by-子句表示 b 将会在 Derived 中内部存储， 并且编译器将生成转发给 b 的所有 Base 的方法
class Derived(b: Base) : Base by b {
    // 在 b 的 `print` 实现中不会访问到这个属性
    override val message = "Message of Derived"

    override fun printMessage() {
        print("abc")
    }
}





/**
 * 委托属性
 * 有一些常见的属性类型，虽然我们可以在每次需要的时候手动实现它们， 但是如果能够为大家把他们只实现一次并放入一个库会更好。例如包括：
 * 延迟属性（lazy properties）: 其值只在首次访问时计算
 * 可观察属性（observable properties）: 监听器会收到有关此属性变更的通知
 * 把多个属性储存在一个映射（map）中，而不是每个存在单独的字段中
 *
 * 为了涵盖这些（以及其他）情况，Kotlin 支持 委托属性:
 * 语法是： val/var <属性名>: <类型> by <表达式>， 在 by 后面的表达式是该委托
 * 因为属性对应的 get()（与 set()）会被委托给它的 getValue() 与 setValue() 方法。 属性的委托不必实现任何的接口，但是需要提供一个 getValue() 函数（与 setValue()——对于 var 属性）
 *
 */

class Example {
    //语法是： val/var <属性名>: <类型> by <表达式>， 在 by 后面的表达式是该委托
    var p: String by Delegate()
}


//属性的委托不必实现任何的接口，但是需要提供一个 getValue() 函数（与 setValue()——对于 var 属性）
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}


/**
 * 标准委托
 * Kotlin 标准库为几种有用的委托提供了工厂方法
 */
//=================================================延迟属性 Lazy
//lazy() 是接受一个 lambda 并返回一个 Lazy <T> 实例的函数，返回的实例可以作为实现延迟属性的委托： 第一次调用 get() 会执行已传递给 lazy() 的 lambda 表达式并记录结果， 后续调用 get() 只是返回记录的结果
//默认情况下，对于 lazy 属性的求值是同步锁的（synchronized）：该值只在一个线程中计算，并且所有线程会看到相同的值。
// 如果初始化委托的同步锁不是必需的，这样多个线程可以同时执行，那么将 LazyThreadSafetyMode.PUBLICATION 作为参数传递给 lazy() 函数。
// 而如果你确定初始化将总是发生在与属性使用位于相同的线程， 那么可以使用 LazyThreadSafetyMode.NONE 模式：它不会有任何线程安全的保证以及相关的开销
val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}

//=============================================可观察属性 Observable
//Delegates.observable() 接受两个参数：初始值与修改时处理程序（handler）。 每当我们给属性赋值时会调用该处理程序（在赋值后执行）。它有三个参数：被赋值的属性、旧值与新值：
//如果你想能够截获一个赋值并“否决”它，就使用 vetoable() 取代 observable()。 在属性被赋新值生效之前会调用传递给 vetoable 的处理程序
class User1 {
    var name: String by Delegates.observable("<no name>") {
        prop, old, new ->
        println("$old -> $new")
    }
}


//============================================把属性储存在映射中
//一个常见的用例是在一个映射（map）里存储属性的值。 这经常出现在像解析 JSON 或者做其他“动态”事情的应用中。 在这种情况下，你可以使用映射实例自身作为委托来实现委托属性
class User2(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int     by map
}
//构造函数接受一个映射参数
val user2 = User2(mapOf(
        "name" to "John Doe",
        "age"  to 25
))
//这也适用于 var 属性，如果把只读的 Map 换成 MutableMap 的话：
class MutableUser(val map: MutableMap<String, Any?>) {
    var name: String by map
    var age: Int     by map
}


//============================================局部委托属性
//你可以将局部变量声明为委托属性。 例如，你可以使一个局部变量惰性初始化：
//memoizedFoo 变量只会在第一次访问时计算。 如果 someCondition 失败，那么该变量根本不会计算
/*fun example(computeFoo: () -> Foo) {
    val memoizedFoo by lazy(computeFoo)

    if (true && memoizedFoo.isValid()) {
        memoizedFoo.doSomething()
    }
}*/





