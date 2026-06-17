package com.ch08.CH08_01

// 介面
interface Fly {
    fun flying() // 抽象方法
}

// 定義 Bird 類別實作 Fly 介面
class Bird : Fly {
    override fun flying() {
        println("鳥在飛行")
    }
}

// 定義 Airplane 類別實作 Fly 介面
class Airplane : Fly {
    override fun flying() {
        println("飛機在飛行")
    }
}

fun main() {
    val bird = Bird()
    bird.flying()

    val airplane = Airplane()
    airplane.flying()
}
