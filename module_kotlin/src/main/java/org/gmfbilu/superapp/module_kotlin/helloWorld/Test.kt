package org.gmfbilu.superapp.module_kotlin.helloWorld

import org.gmfbilu.superapp.module_kotlin.helloWorld.印章类.Son
import org.gmfbilu.superapp.module_kotlin.helloWorld.委托和代理.SmallFather
import org.gmfbilu.superapp.module_kotlin.helloWorld.接口.IMan
import org.gmfbilu.superapp.module_kotlin.helloWorld.枚举.Week
import org.gmfbilu.superapp.module_kotlin.helloWorld.继承.Human
import org.gmfbilu.superapp.module_kotlin.helloWorld.继承.Man
import org.gmfbilu.superapp.module_kotlin.helloWorld.继承.Woman

fun main(args: Array<String>) {
    val man = Man("金三胖")
    val woman = Woman("慈禧太后")
    val man1 = org.gmfbilu.superapp.module_kotlin.helloWorld.接口.Man("接口")
    val listOf = listOf<Human>(man, woman, man1)
    for (h in listOf) {
        if (h is IMan) {
            h.xiaodidi()
        }
    }
    val smallFather = SmallFather()
    smallFather.washing()
    val one = Week.one
    val donkey = Son.驴()
    val luozi = Son.骡子()
    val listOf1 = listOf<Son>(donkey, luozi)
    for (v in listOf1){
       /* if (v in Son.驴){
            v.sayHello()
        }*/
    }
}