package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.印章类

/**
 * 母驴，公驴，公马生出来的儿子：结果是骡子或者驴
 *
 * sealed类没有构造方法，不能使用Son()创建对象。是子类类型有限的类型
 * sealed更在意类型，枚举更在意数据
 */
sealed class Son {

    class 驴: Son()
    class 骡子() : Son()

    fun sayHello() {
        println("大家好")
    }
}