package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型

val range: IntRange = 0..1042 //[0,1024]
val range_exclusive: IntRange = 0 until 1024 //(0,1024]
fun range() {
    for (i in 1..10) { //区间迭代

    }

    for (x in 1..10 step 2) { //遍历从1到10，从1开始，每一步+2。1,3,5,7,9
        //println(x)
    }

    for (x in 9 downTo 0 step 3) {//遍历从9到0，从9开始，每一步-3。9,6,3,0
        println(x)
    }
}