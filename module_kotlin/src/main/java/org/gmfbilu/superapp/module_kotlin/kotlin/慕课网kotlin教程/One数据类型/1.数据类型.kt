package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型


//kotlin主函数
fun main(args: Array<String>) {
}


//=========================================================数据类型
//kotlin没有基本数据类型。Char，Byte，Boolean，Float，Int，Double，Long。Number类型(Float，Int，Double，Long),kotlin不区分装箱类型和非装箱类型
//所有以未超出 Int 最大值的整型值初始化的变量都会推断为 Int 类型。如果初始值超过了其最大值，那么推断为 Long 类型。 如需显式指定 Long 型值，请在该值后追加 l 或 L 后缀
//对于以小数初始化的变量，编译器会推断为 Double 类型。 如需将一个值显式指定为 Float 类型，请添加 f 或 F 后缀
val cities: Double = 0.0 //运行时常量，在编译期不可知道具体的值，只读,可以通过反射修改。后面可以省略数据类型，叫类型推导。只能为其赋值一次
var trees: Int = 0 //可读可写
val isWeek = true //自动推断出 Boolean类型
val isend get() = isWeek // 只读属性赋值
val isMe: Boolean? = true //在数据类型后面加上?表示此数据可能为null,不加就是不可能为null。Boolean也是有可能为null的
const val phone = "iphone" //编译期常量，在编译期可知道具体的值
var distances = 0.0f// 类型 Float、默认 getter 和 setter
val families = 4// 类型 Int、默认 getter
val language: Char = '\n' //换行符
val helloTrump: String = "hello\"trump\"" //hello"trump"

//可以使用下划线使数字常量更易读
val oneMillion = 1_000_000
val creditCardNumber = 1234_5678_9012_3456L
val socialSecurityNumber = 999_99_9999L
val hexBytes = 0xFF_EC_DE_5E
val bytes = 0b11010010_01101001_10010100_10010010

//较小的类型不能隐式转换为较大的类型。 这意味着在不进行显式转换的情况下我们不能把 Byte 型值赋给一个 Int 变量

fun c() {//交换两个变量
    var a = 1
    var b = 2
    a = b.also { b = a }
}

/**
 * 声明一个属性的完整语法是
 * var <propertyName>[: <PropertyType>] [= <property_initializer>]
 * [<getter>]
 * [<setter>]
 */
//每次访问该属性时都会调用它get
val isEmpty: Boolean
    get() = "".length == 0

//定义了一个自定义的 setter，那么每次给属性赋值时都会调用它的set
var stringRepresentation: String
    get() = "".toString()
    set(value) {

    }

//需要改变一个访问器的可见性或者对其注解，但是不需要改变默认的实现， 你可以定义访问器而不定义其实现
var setterVisibility: String = "abc"
    private set // 此 setter 是私有的并且有默认实现

var setterWithAnnotation: Any? = null
//@Inject set // 用 Inject 注解此 setter


//扩展属性，由于扩展没有实际的将成员插入类中，因此对扩展属性来说幕后字段是无效的。这就是为什么扩展属性不能有初始化器。他们的行为只能由显式提供的 getters/setters 定义
val <T> List<T>.lastIndex: Int
    get() = size - 1






