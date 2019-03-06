package org.gmfbilu.superapp.module_kotlin.helloWorld.继承

class Woman(name: String) : Human(name) {

    override fun eat() {
        println("${name}小口小口的吃")
    }
}