package com.ch08.CH08_09

// 定義介面
// Functional Interface（函式介面），限制只能有 一個抽象方法，支援 Lambda 表達式 作為實作。

fun interface Animal {
    fun running()
}

// 一般介面，可有多個方法（抽象或預設），不支援 Lambda 直接實作。
class Dog : Animal {
    override fun running() {
        println("Dog is running.....")
    }
}

fun main() {
    println("......1.......")
    val h1: Animal = Dog()//upcasting
    h1.running()

    println("......2.......")
    // 匿名類別，物件方式實作介面
    val h2: Animal = object: Animal {
        override fun running() {
            println("h2.......")
        }
    }
    h2.running()
    // 一次性使用
    object : Animal {
        override fun running() {
            println("h3.......")
        }
    }.running()

    // Lambda 表示法（介面且只有一個抽象方法才可用）
    val h4 = Animal {
        println("h4-1.......")
    }
    h4.running()

    // 一次性使用
    Animal {
        println("h4-2.......")
    }.running()
}
