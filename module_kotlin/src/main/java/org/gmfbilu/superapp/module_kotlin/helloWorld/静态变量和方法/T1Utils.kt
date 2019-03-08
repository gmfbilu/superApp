package org.gmfbilu.superapp.module_kotlin.helloWorld.静态变量和方法

import android.widget.Toast

/**
 * 在 Kotlin 将这种方式称之为伴生对象
 */
object T1Utils {

    var sToast: Toast? = null

    fun show() {
        sToast!!.show()
    }
}