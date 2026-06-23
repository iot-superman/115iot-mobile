package com.ch09.CH09_02

// 定義泛型類別
class Test<K, V>(private val key: K, private val value: V) {
    fun getKey(): K = key
    fun getValue(): V = value
}

fun main() {
    val t = Test(10, "abc")  // 型別推論可省略 <Int, String>
    println(t.getKey())
    println(t.getValue())
}
