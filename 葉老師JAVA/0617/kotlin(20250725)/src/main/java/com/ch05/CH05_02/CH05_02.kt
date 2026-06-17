package com.ch05.CH05_02
//建構方法說明
class SmallMath(private val x: Int, private val y: Int) {
    fun add() {
        println("加法結果 : ${x + y}")
    }

    fun mul() {
        println("乘法結果 : ${x * y}")
    }
}
//or
class SmallMath2(private val x: Int, private val y: Int) {
    fun add(): Int = x + y
    fun mul(): Int = x * y
}

fun main() {
    val a = SmallMath(5, 10)  // 建立物件
    a.add()                   // 列印加法結果
    a.mul()                   // 列印乘法結果
}
