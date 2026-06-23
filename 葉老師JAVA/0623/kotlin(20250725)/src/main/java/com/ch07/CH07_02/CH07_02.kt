package com.ch07.CH07_02
// 抽象類別
abstract class Cal(val x: Int, val y: Int) {
    init {
        println("初始化數值")
    }

    // 普通方法
    fun multiply(): Double {
        return (x * y).toDouble()
    }

    // 抽象方法
    abstract fun answer(): Double
}

// 加法類別
class CalPlus(x: Int, y: Int) : Cal(x, y) {
    override fun answer(): Double {
        return (x + y).toDouble()
    }
}

// 減法類別
class CalMinus(x: Int, y: Int) : Cal(x, y) {
    override fun answer(): Double {
        return (x - y).toDouble()
    }
}


fun main() {
    val myPlus = CalPlus(10, 20)
    println(myPlus.multiply())
    println(myPlus.answer())

    val myMinus = CalMinus(10, 20)
    println(myMinus.multiply())
    println(myMinus.answer())
}
