package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.继承

class Man(name: String) : Human(name) {
    override fun eat() {
        println("${name}大口大口的吃")
    }

}