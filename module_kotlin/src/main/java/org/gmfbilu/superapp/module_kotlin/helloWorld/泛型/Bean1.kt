package org.gmfbilu.superapp.module_kotlin.helloWorld.泛型

class Bean1<T : Comparable<String>>(t: T) {
    var data = t
}
//var bean = Bean("666666")