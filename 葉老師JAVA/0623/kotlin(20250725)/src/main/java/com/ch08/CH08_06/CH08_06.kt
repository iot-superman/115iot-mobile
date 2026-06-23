package com.ch08.CH08_06

// 定義 Bird 介面
interface Bird {
    fun birdFly()
}

// 定義 Airplane 介面
interface Airplane {
    fun airplaneFly()
}

// 定義 Fly 介面繼承 Bird 和 Airplane
interface Fly: Bird, Airplane {
    fun pediaFly()
}

// 定義 InfoFly 類別實作 Fly（間接實作 Bird 和 Airplane）
class InfoFly : Fly {
    override fun birdFly() {
        println("鳥用翅膀飛")
    }

    override fun airplaneFly() {
        println("飛機用引擎飛")
    }

    override fun pediaFly() {
        println("飛行百科")
    }
}

fun main() {
    val obj = InfoFly()
    obj.birdFly()
    obj.airplaneFly()
    obj.pediaFly()
}
