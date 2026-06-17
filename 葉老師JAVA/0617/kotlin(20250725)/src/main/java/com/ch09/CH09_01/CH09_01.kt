package com.ch09.CH09_01

// 定義泛型類別
class MyData<T> (private val obj: T){
    fun getObj():T{
        return obj
    }
}

fun main() {
    val i = MyData(10)
    println("value=${i.getObj()}")

    val d = MyData(12.5555)
    println("value=${d.getObj()}")

    val str = MyData("bill")
    println("value=${str.getObj()}")

}
