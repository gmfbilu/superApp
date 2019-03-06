package org.gmfbilu.superapp.module_kotlin.helloWorld

/**
 * 父类要使用open修饰符才能被子类继承，不写的话默认是final
 */
open class Father {

    var charactor: String = "性格内向"

    /**
     * 要被重写的方法需要添加open修饰符，不写的话默认不能被重写
     */
    open fun action() {
        println("公共场合喜欢大声喧哗")
    }
}