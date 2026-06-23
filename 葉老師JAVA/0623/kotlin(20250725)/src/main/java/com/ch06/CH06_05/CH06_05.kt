package com.ch06.CH06_05
//繼承+父類別建構方法有代資料+super()
open class Animal(private val name: String) {
    init {
        println("Animal....")
    }

    open fun eat() {
        println("$name 正在吃食物")
    }

    open fun sleep() {
        println("$name 正在睡覺")
    }
}

class Dog(name: String) : Animal(name) {
    init {
        println("Dog....")
    }
}

fun main() {
    val dog = Dog("Haly")
    dog.eat()
    dog.sleep()
}
