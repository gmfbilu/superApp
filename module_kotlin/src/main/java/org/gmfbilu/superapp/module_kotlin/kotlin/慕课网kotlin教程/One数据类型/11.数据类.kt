package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型

/**
 * 创建一些只保存数据的类,数据类并标记为data
 *
 * 编译器自动从主构造函数中声明的所有属性导出以下成员：
 * equals()/hashCode()
 * toString() 格式是 "User(name=John, age=42)"
 * componentN() 函数 按声明顺序对应于所有属性
 * copy() 函数
 *
 * 为了确保生成的代码的一致性以及有意义的行为，数据类必须满足以下要求：
 * 主构造函数需要至少有一个参数；
 * 主构造函数的所有参数需要标记为 val 或 var；
 * 数据类不能是抽象、开放、密封或者内部的；
 *
 * 此外，成员生成遵循关于成员继承的这些规则：
 * 如果在数据类体中有显式实现 equals()、 hashCode() 或者 toString()，或者这些函数在父类中有 final 实现，那么不会生成这些函数，而会使用现有函数；
 * 如果超类型具有 open 的 componentN() 函数并且返回兼容的类型， 那么会为数据类生成相应的函数，并覆盖超类的实现。如果超类型的这些函数由于签名不兼容或者是 final 而导致无法覆盖，那么会报错；
 * 不允许为 componentN() 以及 copy() 函数提供显式实现
 * 如果生成的类需要含有一个无参的构造函数，则所有的属性必须指定默认值
 *
 * 在类体中声明的属性
 * 请注意，对于那些自动生成的函数，编译器只使用在主构造函数内部定义的属性。如需在生成的实现中排出一个属性，请将其声明在类体中：
 * 在 toString()、 equals()、 hashCode() 以及 copy() 的实现中只会用到主构造方法中的属性属性，在在类体中声明的属性不会被用到
 *
 *
 * 复制
 * 在很多情况下，我们需要复制一个对象改变它的一些属性，但其余部分保持不变。 copy() 函数就是为此而生成
 * val jack = User(name = "Jack", age = 1)
 * val olderJack = jack.copy(age = 2)
 *
 * 数据类与解构声明
 * 为数据类生成的 Component 函数 使它们可在解构声明中使用：
 * val jane = User("Jane", 35)
 * val (name, age) = jane
 * println("$name, $age years of age") // 输出 "Jane, 35 years of age"
 */
data class User(val name: String, val age: Int)