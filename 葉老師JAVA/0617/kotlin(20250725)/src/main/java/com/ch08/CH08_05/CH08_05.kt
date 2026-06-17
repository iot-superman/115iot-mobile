package com.ch08.CH08_05

// 定義 Bird 介面
interface Bird {
    fun birdFly()
}

// 定義 Airplane 介面
interface Airplane {
    fun airplaneFly()
}

// Fly 類別同時實作 Bird 和 Airplane 介面
class Fly6 : Bird, Airplane {
    override fun birdFly() {
        println("鳥用翅膀飛")
    }

    override fun airplaneFly() {
        println("飛機用引擎飛")
    }

    fun pediaFly() {
        println("飛行百科")
    }
}

fun main() {
    val f = Fly6()
    f.airplaneFly()
    f.birdFly()
    f.pediaFly()
}
