package com.ch06.CH06_06
//繼承+多個子類別
open class Animal(var name: String) {
    open fun eat() {
        println("$name 正在吃食物")
    }

    open fun sleep() {
        println("$name 正在睡覺")
    }
}

class Dog(name: String) : Animal(name) {
    fun barking() {
        println("$name 正在叫")
    }
}

class Bird(name: String) : Animal(name) {
    fun flying() {
        println("$name 正在飛")
    }
}

fun main() {
    val dog = Dog("Haly")
    dog.eat()      // 繼承自 Animal
    dog.sleep()    // 繼承自 Animal
    dog.barking()  // Dog 自有方法

    dog.name = "ccc"  // 可修改 protected 成員
    dog.barking()

    val bird = Bird("Cici")
    bird.eat()
    bird.sleep()
    bird.flying()
}
