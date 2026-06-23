package com.ch06.CH06_07
//多層繼承+建構方法有帶入值
open class Animal(var name: String) {
    open fun eat() {
        println("$name 正在吃食物")
    }
}

open class Mammal(name: String, var favoriteFood: String) : Animal(name) {
    fun favoriteFood() {
        println("$name 喜歡吃 $favoriteFood")
    }
}

class Cat(name: String, favoriteFood: String) : Mammal(name, favoriteFood) {
    fun jumping() {
        println("$name 正在叫")
    }
}

fun main() {
    val cat = Cat("lucy", "fish")
    cat.eat()             // 繼承 Animal
    cat.favoriteFood()    // 繼承 Mammal
    cat.jumping()         // Cat 類自有方法

    // 若要存取 protected 成員需額外包裝為 public getter
    println("name=${cat.name}")
    println("favoriteFood=${cat.favoriteFood}")
}
