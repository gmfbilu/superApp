package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld2

data class Girl(var name: String, var age: Int, var height: Int, var address: String)

var 非诚勿扰数据库 = listOf<Girl>(
        Girl("A", 18, 168, "长沙"),
        Girl("B", 18, 168, "青岛"),
        Girl("C", 18, 168, "哈尔滨"),
        Girl("D", 18, 168, "北京"),
        Girl("E", 18, 168, "厦门"),
        Girl("F", 18, 168, "上海"),
        Girl("G", 18, 168, "武汉"),
        Girl("H", 18, 168, "南京"),
        Girl("I", 18, 168, "苏州")
)

fun filterGirlByAddress(address: String) {
    var 某个地区的妹子 = ArrayList<Girl>()
    for (girl in 非诚勿扰数据库) {
        if (girl.address.equals(address)) {
            某个地区的妹子.add(girl)
        }
    }
    for (girl in 某个地区的妹子) {
        println("${girl.name}")
    }
}