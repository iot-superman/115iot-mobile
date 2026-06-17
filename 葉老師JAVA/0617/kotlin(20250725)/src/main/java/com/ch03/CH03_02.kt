package com.ch03

fun f21() {
    println("function return no value")
}

//fun f22(): Int {
//    return 2
//}

fun f22(): Int = 2


//fun f23(): Double {
//    return 3.14
//}

fun f23(): Double = 3.14

fun main() {
    f21()
    val n = f22()
    println("n=$n")         // 2
    println("pi=${f23()}")   // 3.14
}
