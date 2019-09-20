package org.gmfbilu.superapp.module_kotlin

class Test {

    fun main(): Int {
        val sb = StringBuffer()
        sb.add("")
        return 0
    }

    fun StringBuffer.add(str: String) {
        this.append(str)
    }

}