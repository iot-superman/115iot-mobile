package com.ch08.CH08_04

// 定義 Animal 介面
interface Animal {
    fun showMe()
}

// 定義 Bird 介面繼承 Animal
interface Bird: Animal {
    fun flying()
}

// 定義 Eagle 類別實作 Bird（間接實作 Animal）
class Eagle : Bird {
    override fun showMe() {
        println("我是動物")
    }

    override fun flying() {
        println("我是老鷹我會飛")
    }
}

fun main() {
    val eagle = Eagle()
    eagle.showMe()
    eagle.flying()
}
