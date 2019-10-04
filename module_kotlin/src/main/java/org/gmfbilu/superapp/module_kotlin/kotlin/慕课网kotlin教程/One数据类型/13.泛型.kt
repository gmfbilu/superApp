package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型

/**
 * 与 Java 类似，Kotlin 中的类也可以有类型参数：
 * 一般来说，要创建这样类的实例，我们需要提供类型参数：
 * val box: Box<Int> = Box<Int>(1)
 * 但是如果类型参数可以推断出来，例如从构造函数的参数或者从其他途径，允许省略类型参数：
 * val box = Box(1) // 1 具有类型 Int，所以编译器知道我们说的是 Box<Int>。
 *
 *
 *
 * 型变
 * Java类型系统中最棘手的部分之一是通配符类型。 而 Kotlin 中没有，它有两个其他的东西：声明处型变与类型投影
 * Java 中的泛型是不型变的，这意味着 List<String> 并不是 List<Object> 的子类型
 *
 * 一般原则是：当一个类C的类型参数T被声明为out时，它就只能出现在C成员的输出-位置，但回报是C<Base>可以安全地作为C<Derived>的超类
 * 类C是在参数T上是协变的，或者说T是一个协变的类型参数。 你可以认为C是T的生产者，而不是T的消费者
 * out修饰符称为型变注解，并且由于它在类型参数声明处提供，所以我们称之为声明处型变
 * in使得一个类型参数逆变：只可以被消费而不可以被生产。逆变类型的一个很好的例子是 Comparable
 * 消费者in, 生产者out
 *
 * 类型投影
 * Array<in String> 对应于 Java 的 Array<? super String>，也就是说，你可以传递一个 CharSequence 数组或一个 Object 数组
 *
 * 星投影
 * 对类型参数一无所知，但仍然希望以安全的方式使用它。 这里的安全方式是定义泛型类型的这种投影，该泛型类型的每个具体实例化将是该投影的子类型
 * Kotlin 为此提供了所谓的星投影语法：
 * 对于 Foo <out T : TUpper>，其中 T 是一个具有上界 TUpper 的协变类型参数，Foo <*> 等价于 Foo <out TUpper>。 这意味着当 T 未知时，你可以安全地从 Foo <*> 读取 TUpper 的值
 * 对于 Foo <in T>，其中 T 是一个逆变类型参数，Foo <*> 等价于 Foo <in Nothing>。 这意味着当 T 未知时，没有什么可以以安全的方式写入 Foo <*>
 * 对于 Foo <T : TUpper>，其中 T 是一个具有上界 TUpper 的不型变类型参数，Foo<*> 对于读取值时等价于 Foo<out TUpper> 而对于写值时等价于 Foo<in Nothing>
 * 如果泛型类型具有多个类型参数，则每个类型参数都可以单独投影
 * 例如，如果类型被声明为 interface Function <in T, out U>，我们可以想象以下星投影：
 * Function<*, String> 表示 Function<in Nothing, String>；
 * Function<Int, *> 表示 Function<Int, out Any?>；
 * Function<*, *> 表示 Function<in Nothing, out Any?>。
 * 星投影非常像 Java 的原始类型，但是安全
 *
 *
 *
 *
 * 泛型约束
 * fun <T : Comparable<T>> sort(list: List<T>) {  …… }
 * 默认的上界（如果没有声明）是 Any?。在尖括号中只能指定一个上界。 如果同一类型参数需要多个上界，我们需要一个单独的 where-子句
 *
 * 类型擦除
 * Kotlin 为泛型声明用法执行的类型安全检测仅在编译期进行。 运行时泛型类型的实例不保留关于其类型实参的任何信息。
 * 其类型信息称为被擦除。例如，Foo<Bar> 与 Foo<Baz?> 的实例都会被擦除为 Foo<*>
 *
 */
class Box<T>(t: T) {
    var value = t
}

val box = Box<Int>(1)


interface Source<out T> {
    fun nextT(): T
}

fun demo(strs: Source<String>) {
    val objects: Source<Any> = strs // 这个没问题，因为 T 是一个 out-参数

}

interface Comparable<in T> {
    operator fun compareTo(other: T): Int
}

fun demo(x: Comparable<Number>) {
    x.compareTo(1.0) // 1.0 拥有类型 Double，它是 Number 的子类型
    // 因此，我们可以将 x 赋给类型为 Comparable <Double> 的变量
    val y: Comparable<Double> = x // OK！
}


//泛型函数
fun <T> singletonList1(item: T): List<T>? {
    return null
}

// 泛型扩展函数
fun <T> T.basicToString1(): String? {
    //要调用泛型函数，在调用处函数名之后指定类型参数即可
    val l = singletonList1<Int>(1)
    //可以省略能够从上下文中推断出来的类型参数，所以以下示例同样适用
    val l1 = singletonList1(1)
    return null
}


//冒号之后指定的类型是上界：只有 Comparable<T> 的子类型可以替代 T
//sort(listOf(1, 2, 3)) // OK。Int 是 Comparable<Int> 的子类型
//sort(listOf(HashMap<Int, String>())) // 错误：HashMap<Int, String> 不是 Comparable<HashMap<Int, String>> 的子类型
fun <T : Comparable<T>> sort(list: List<T>) {

}

//所传递的类型必须同时满足 where 子句的所有条件。类型 T 必须既实现了 CharSequence 也实现了 Comparable
fun <T> copyWhenGreater(list: List<T>, threshold: T): List<String>
        where T : CharSequence,
              T : Comparable<T> {
    return list.filter { it > threshold }.map { it.toString() }
}