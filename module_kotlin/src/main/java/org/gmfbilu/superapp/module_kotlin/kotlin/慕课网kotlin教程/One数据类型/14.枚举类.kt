package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator

/**
 * 枚举类的最基本的用法是实现类型安全的枚举
 * 每个枚举常量都是一个对象。枚举常量用逗号分隔
 */
enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

/**
 * 因为每一个枚举都是枚举类的实例，所以他们可以是这样初始化过的
 */
enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

/**
 * 匿名类
 * 枚举常量还可以声明其带有相应方法以及覆盖了基类方法的匿名类
 * 如果枚举类定义任何成员，那么使用分号将成员定义中的枚举常量定义分隔开
 */
enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING
    },

    TALKING {
        override fun signal() = WAITING
    };

    abstract fun signal(): ProtocolState
}


/**
 * 在枚举类中实现接口
 * 一个枚举类可以实现接口（但不能从类继承），可以为所有条目提供统一的接口成员实现，也可以在相应匿名类中为每个条目提供各自的实现。只需将接口添加到枚举类声明中即可
 */
@RequiresApi(Build.VERSION_CODES.N)
enum class IntArithmetics : BinaryOperator<Int>, IntBinaryOperator {
    PLUS {
        override fun apply(t: Int, u: Int): Int = t + u
    },
    TIMES {
        override fun apply(t: Int, u: Int): Int = t * u
    };

    override fun applyAsInt(t: Int, u: Int) = apply(t, u)
}