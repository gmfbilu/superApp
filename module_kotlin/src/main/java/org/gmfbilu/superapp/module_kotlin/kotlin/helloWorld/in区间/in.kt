package org.gmfbilu.superapp.module_kotlin.kotlin.helloWorld.in区间

fun main(args:Array<String>){

}

//是否是字符,在区间内
fun isLetter(c:Char)=c in 'a'..'z'||c in 'A'..'Z'

//不在区间内
fun isNotDigit(c:Char)=c !in '0'..'9'

fun recognize(c:Char)=when(c){
    in '0'..'9' -> "it is a digit" 
    in 'a'..'z',in 'A'..'Z' -> "it is a letter"
    else -> "i do not know"
}