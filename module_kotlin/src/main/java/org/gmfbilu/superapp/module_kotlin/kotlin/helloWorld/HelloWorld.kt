package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld

import org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.静态变量和方法.T1Utils
import java.math.BigInteger

/**
 * Java写法——>kotlin写法
 *
 * 可见修饰符：
 * Java 的写法（默认为 default）
 * public	所有类可见
 * protected	子类可见
 * default	同一包下的类可见
 * private	仅对自己类可见
 * Kotlin 的写法（默认为 public）
 * public	所有类可见
 * internal	同 Module 下的类可见
 * protected	子类可见
 * private	仅对自己类可见
 *
 * MainActivity.this——>this@MainActivity
 *
 * MainActivity.class——>MainActivity::class.java
 *
 * 继承：public class MainActivity extends AppCompatActivity——>class MainActivity : AppCompatActivity()。在 Kotlin 中被继承类必须被 open 关键字修饰）
 *
 * 变量：Intent intent = new Intent()——>var intent = Intent()
 *
 * 常量：final String text = "hello"——>val text = "hello"
 *
 * 静态常量：static final String text = "hello"——>const val text = "hello"。（需要注意的是要把静态变量定义在类上方）
 *
 * 比较类型："hello" instanceof String——>"hello" is String
 *
 * 比较字符串：s1.equals(s2)——>s1 == s2。（Kotlin 对字符串比较的写法进行优化了，其他类型对象对比还是要用 equals 方法）
 *
 * 数组：int[] array1 = {1, 2, 3}——>val array1 = intArrayOf(1, 2, 3)。String[] array3 = {"1", "2", "3"}——>val array3 = arrayListOf("1", "2", "3")
 *
 * 循环：for (int i = 0; i < array.length; i++)——>for (i in array.indices)。（for (i in 1 until array.size) ）
 *
 * 高级循环：for (String text : array)——>for (text in array)
 *
 * 判断器：
 * int count = 1;
 * switch (count) {
 *  case 0:
 *    System.out.println(count);
 *    break;
 *  case 1:
 *  case 2:
 *    System.out.println(count);
 *    break;
 *  default:
 *    System.out.println(count);
 *    break;
 *  }
 * ——>
 * when (count) {
 *  0 -> {
 *    println(count)
 *  }
 *  in 1..2 -> {
 *    println(count)
 *  }
 *  else -> {
 *    println(count)
 *  }
 * }
 * 更简洁的写法
 * when (count) {
 *  0 -> println(count)
 *  in 1..2 -> println(count)
 *  else -> println(count)
 * }
 *
 * 构造函数：
 * class MyView : View {
 *  constructor(context : Context) : this(context, null)
 *  constructor(context : Context, attrs : AttributeSet?) : this(context, attrs, 0)
 *  constructor(context : Context, attrs : AttributeSet?, defStyleAttr : Int) : super(context, attrs, defStyleAttr)
 * }
 *
 * 可变参数：
 * int... array——>vararg array: Int
 *
 */

//静态常量
const val text = "hello"

class HelloWorld {

    /**
     * main函数
     */
    fun main(array: Array<String>) {
        //类型推断i为int,vars声明变量
        var i = 0
        //i=9999999999
        //显示指定类型
        var i1: Int = 0
        //类型推断j为long
        var j = 9999999999
        //没有初始值
        var str: String

        //val是只读的数据类型,val声明常量
        val num = 0
        val a: Byte = Byte.MAX_VALUE

        //表示d一定不为null
        var d = i!!

        //字符串模板
        val name = "messi"
        println("我的名字叫$name,我的名字有${name.length}个字母")

        //函数表达式
        var b = { x: Int, y: Int -> x + y }
        var bResult = b(3, 5)
        var c: (Int, Int) -> Int = { x, y -> x + y }
        var cResult = c(3, 4)

        //具名参数
        hello5(r = 1.0f)

        //and和&&类似
        if (true and false) {

        }

        T1Utils.sToast
    }


    /**
     * 无参数无返回值函数
     */
    fun print() {
        println("无参数无返回值函数")
    }

    /**
     * 无参数有返回值函数
     */
    fun add(): Int {
        return 0
    }

    /**
     *有参数无返回值函数,参数不能为null
     */
    fun plus(a: Int, b: Int) {
        var c = a * b
    }

    /**
     * 有参数有返回值函数,参数可以为null
     */
    fun input(a: Byte?): String {
        //[a+""]和[""+a]不一样
        return "$a"
    }

    /**
     * when表达式
     */
    fun whenF(i: Int) {
        when (i) {
            0 -> println("考鸭蛋")
            1 -> println("考得还不错")
            else -> println("考得怎么样")
        }

        //kotlin中when有返回值
        var score = when (i) {
            0 -> {
                "零分"
            }
            1 -> "一分"
            else -> ""
        }
    }


    /**
     * 数组
     */
    fun hello1() {
        //声明一个[1,2..100]的集合
        var nums = 1..100
        //声明一个[1,2..100)的集合
        var nums1 = 1 until 100
        for (num in nums) {
            print("$num,")
        }
        //跳过两步
        for (num in nums1 step 2) {
            //集合大小
            print("${nums1.count()}")
        }
    }

    /**
     * List
     */
    fun hello2() {
        val list = listOf("A", "B", "C", "D")
        //i是index,e是index对应的值
        for ((i, e) in list.withIndex()) {
            println("$i  $e")
        }
    }

    /**
     * Map
     */
    fun hello3() {
        val hashMap = HashMap<String, String>()
        hashMap["1"] = "A"
        hashMap["2"] = "B"
        var value = hashMap["2"]
    }

    /**
     * 函数有返回值，且函数体只有一行可以采用下面方式书写
     */
    fun hello4(a: Int, b: Int): Int = a + b


    /**
     * 默认参数
     */
    val pi = 3.14f

    fun hello5(PI: Float = pi, r: Float): Float {
        return PI * r * 2
    }

    /**
     * 递归，阶乘
     */
    fun hello6(a: BigInteger): BigInteger {
        if (a == BigInteger.ONE) {
            return BigInteger.ONE
        } else {
            return a * hello6(a - BigInteger.ONE)
        }
    }

    /**
     * 函数修饰符，不写默认就是public
     */
    private fun hello7() {

    }
}