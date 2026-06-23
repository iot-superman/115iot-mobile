package com.ch07.CH07_03

// 抽象類別
abstract class MyMath {
    abstract fun add(n1: Int, n2: Int): Int
    abstract fun cut(n1: Int, n2: Int): Int

    fun output() {
        println("我的計算器")
    }
}

// 子類別 MyTest 繼承 MyMath
class MyTest: MyMath() {
    override fun add(num1: Int, num2: Int): Int {
        return num1 + num2
    }

    override fun cut(num1: Int, num2: Int): Int {
        return num1 - num2
    }

    fun mul(num1: Int, num2: Int): Int {
        return num1 * num2
    }

    fun divide(num1: Int, num2: Int): Double {
        return num1.toDouble()/num2
    }
}

fun main() {
    val obj1 = MyTest()
    obj1.output()
    println("加法結果 : ${obj1.add(3, 8)}")
    println("減法結果 : ${obj1.cut(3, 8)}")
    println("乘法結果 : ${obj1.mul(3, 8)}")
    println("除法結果 : ${obj1.divide(3, 8)}")
}
