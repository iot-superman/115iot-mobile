package com.ch06.CH06_04_02

open class Animal {
    var name: String = ""  // 預設 public，在 Kotlin 中沒有 "default" 關鍵字

    fun eat() { // Animal方法eat
        println("$name 正在吃食物")
    }

    fun sleep() { // Animal方法sleep
        println("$name 正在睡覺")
    }
}

class Dog(name: String) : Animal() {

}

fun main() {
    val dog = Dog("Haly")
    dog.eat()   // dog 繼承 Animal 方法 eat()
    dog.sleep() // dog 繼承 Animal 方法 sleep()
}