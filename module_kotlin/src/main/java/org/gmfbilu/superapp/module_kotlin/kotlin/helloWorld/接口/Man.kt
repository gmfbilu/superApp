package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.接口

import org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.继承.Human

/**
 * 实现接口，继承抽象类
 */
class Man(name: String) : IMan, Human(name) {
    override fun eat() {
        println("${name}")
    }

    override fun xiaodidi() {
        println("${name}的有这么大")
    }

}