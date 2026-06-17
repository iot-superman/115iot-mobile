package com.ch04.CH04_03

class Circle {
    var radius: Int = 0
    fun area(): Double {
        val pi = 3.14
        return pi * radius * radius
    }
}

fun main() {
    print("請輸入一個數字: ")
    val num1 = readLine()?.toIntOrNull()

    if (num1 != null) {
        println("您輸入第一個數字為：$num1")

        val c = Circle()
        c.radius = num1

        println("Circle Area = ${c.area()}")

    } else {
        println("輸入無效，請輸入一個有效的整數。")
    }
}
