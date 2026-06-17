package com.ch06.CH06_09
//向上轉型、向下轉型
open class Animal {
    var num_s = 100

    open fun walk() {
        println("Animal is walking.")
    }

    fun test1() {
        println("test1....")
    }
}

class Dog : Animal() {
    var num_d = 200

    override fun walk() {
        println("Dog is walking")
    }

    fun test2() {
        println("test2....")
    }
}

fun main() {
    println("....Upcasting....")
    println("....子 to 父....")

    val obj1: Animal = Dog() // Upcasting
    obj1.walk()
    println(obj1.num_s)

    println("....Downcasting....")
    println("....父 to 子....")

    val obj2 = obj1 as Dog //明確轉回子類別
    obj2.walk()     // 子類別重新定義的方法
    obj2.test1()    // 父類別的方法
    obj2.test2()    // 子類別的方法
    println(obj2.num_s) // 父類別屬性
    println(obj2.num_d) // 子類別屬性
}