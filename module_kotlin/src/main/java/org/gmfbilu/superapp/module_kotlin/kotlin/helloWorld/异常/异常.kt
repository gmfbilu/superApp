package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.异常

import java.io.BufferedReader
import java.lang.NumberFormatException

fun main(args: Array<String>) {

}

fun readNumber(reader: BufferedReader): Int? {
    try {
        val readLine = reader.readLine()
        return Integer.parseInt(readLine)
    } catch (e: NumberFormatException) {
        return null
    } finally {
        reader.close()
    }
}

//tyr也是一个表达式，可以把它的值赋值给一个变量
fun readNumber2(reader: BufferedReader) {
    val number = try {
        Integer.parseInt(reader.readLine())//返回
    } catch (e: NumberFormatException) {
        null//返回
    }
}