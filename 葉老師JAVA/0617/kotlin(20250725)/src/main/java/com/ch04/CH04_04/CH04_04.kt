package com.ch04.CH04_04
// this說明
class ShadowingTest {
    var x: Int = 10
    var y: Int = 50

    fun printInfo(x: Int) {
        println("區域變數 $x")
        println("成員屬性 ${this.x}")
        println("成員屬性 ${this.y}")
        println("成員屬性 $y") // this 可省略
    }
}

fun main() {
    val a = ShadowingTest()
    a.printInfo(20)
}
