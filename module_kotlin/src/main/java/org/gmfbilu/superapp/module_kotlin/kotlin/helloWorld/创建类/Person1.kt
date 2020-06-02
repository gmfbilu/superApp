package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.创建类

 class Person1 {

    var name: String? = null
        get() = field
        set(value) {
            field = value
        }

    var age: Int = 0
        get() = field
        set(value) {
            field = value
        }
}