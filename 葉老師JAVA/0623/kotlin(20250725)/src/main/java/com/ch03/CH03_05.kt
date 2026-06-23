package com.ch03

fun star() {
    repeat(20) {
        print("*")
    }
    println()
}

fun sumN2N(begin: Int, end: Int): Int {
    var sum = 0
    for (i in begin..end) {
        sum += i
    }
    return sum
}

fun operation(num1: Int, num2: Int): DoubleArray {
    val sum = num1 + num2.toDouble()
    val cut = num1 - num2.toDouble()
    val multiply = num1 * num2.toDouble()
    val divide = num1.toDouble() / num2
    return doubleArrayOf(sum, cut, multiply, divide)
}

fun f1(x: Int): Int = x * x + x + 1

fun f2(x: Int, y: Int): Int = x * x + y * y + 1

fun main() {
    println("........1..........")
    repeat(3) {
        repeat(20) { print("*") }
        println()
    }

    println("........2..........")
    repeat(3) {
        star()
    }

    println("........3..........")
    println(sumN2N(1, 100))
    println(sumN2N(1, 10))

    println("........4..........")
    val results = operation(50, 9)
    results.forEach { println(it) }
}