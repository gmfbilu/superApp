package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.迭代

import okhttp3.internal.Internal
import java.util.*

class DieDai {
}

fun main(args:Array<String>){

    for(i in 0..10){
        //上面这种区间是会从0到10迭代11次
        //println(i)
    }


    for (i in 0 until 10){
        //上面这种区间是会从0到9迭代10次
        //println(i)
    }

    for (i in 0..10-1){
        //这种等同于上面until
    }

    for (i in 100 downTo 1 step 2){
        //100 downTo 1 是递减的数列，步长为-1
        //step 2把步长设置成了-2
        //println(i)
        //100，98，96，..，2
    }



    val binaryReps = TreeMap<Char, String>()
    //..不仅可以迭代数字区间，还可以迭代字符区间
    for (i in 'A'..'F'){
        //把ASCII码转换成二进制
        val binary = Integer.toBinaryString(i.toInt())
        //根据键来访问和更新map的语法，可以使用map[key]读取值，并使用map[key]=value来设置它们
        // binaryReps[i]=binary等价于binaryReps.put(i,binary)
        binaryReps[i]=binary
    }
    //迭代map，把键和值赋值给两个字段。letter是key,binary是value
    for ((letter,binary) in binaryReps){
        //println("$letter=$binary")
    }

    val arrayListOf = arrayListOf("12", "3", "1")
    //迭代list，把下标和值赋值给index和element
    for ((index,element) in arrayListOf.withIndex()){
        //println("$index:$element")
    }

}