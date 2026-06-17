package com.ch06.CH06_03
//建構方法+繼承
open class A {
    init {
        println("A is executing...")
    }
}

class B : A() {
    init {
        println("B is executing...")
    }
}

fun main() {
    val b = B() // 執行順序：先 A 的建構區塊，再 B 的
}
