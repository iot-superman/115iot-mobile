package com.ch07.CH07_01
//抽象類別
abstract class Car {
    // 抽象方法
    abstract fun run()

    abstract fun test(message: String)

    // 建構子（init 區塊）
    init {
        println("有車子了")
    }

    // 一般實體方法
    fun refuel() {
        println("汽車加油")
    }
}

class Bmw : Car() {
    init {
        println("有車子BMW了")
    }

    override fun run() {
        println("安全駕駛中 ...")
    }

    override fun test(message: String) {
        println(message)
    }
}

fun main() {
    //val c = Car()//false
    val bmw = Bmw()
    bmw.refuel()
    bmw.run()
    bmw.test("test..........")
}
