package org.gmfbilu.superapp.module_kotlin.kotlin.慕课网kotlin教程.One数据类型

/**
 * 数组在 Kotlin 中使用 Array 类来表示.
 * 使用库函数 arrayOf() 来创建一个数组并传递元素值给它，这样 arrayOf(1, 2, 3) 创建了 array [1, 2, 3]
 *
 */
val items = listOf("apple", "banana", "kiwifruit")
val bookList: IntArray = intArrayOf(0, 2, 8, 1)
val nameList: CharArray = charArrayOf('1', '2')
val cityList: Array<String> = arrayOf("香港", "拉萨")
//循环遍历
fun getSingle() {

    for (book in bookList) {
        println(book)
    }

    bookList.forEach {
        println(it)
    }

    //这里的return会结束整个getSingle函数
    bookList.forEach {
        if (it.equals("a")) {
            return
        }
        println(it)
    }

    //这里的return只会结束掉此循环
    bookList.forEach ForEach@{
        if (it.equals("a")) {
            return@ForEach
        }
        println(it)
    }
}



//===============================================================集合遍历
fun itrat(args: Array<String>) {
    //遍历
    args.map {
        print(it)
    }

    val list = ArrayList<String>()
    val map = HashMap<String, String>()
    for (i in 1..10) {
        list.add(i.toString())
        map.put("key = " + i, "value = " + i)
    }
    //迭代遍历
    for (value in list) {
        println(value)
    }
    //迭代遍历，带有index
    for ((index, value) in list.withIndex()) {
        println("index = $index  value = $value")
    }
    //迭代遍历，带有index
    for ((key, value) in map) {
        println("key = $key  value = $value")
    }
    val mapString = mapOf("a" to 1, "b" to 2, "c" to 3)
    println(mapString["key"])
}
