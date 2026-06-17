package com.ch08.CH08_08

// 定義 Dog 介面
interface Dog {
    fun running()
}

// 定義 Horse 類別
open class Horse {
    fun who() {
        println("我是馬")
    }
}

// Pet 類別繼承 Horse 並實作 Dog
class Pet : Horse(), Dog {
    override fun running() {
        println("寵物在跑")
    }
}

fun main() {
    val obj = Pet()
    obj.who()
    obj.running()
}
