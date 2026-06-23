package com.ch09.CH09_03
import java.util.function.Consumer

// 不會改內容或長度 → listOf(...)（語意最清楚、介面安全）
// 要改元素但長度固定 → arrayOf(...)（或 IntArray/DoubleArray）
// 要增刪元素 → mutableListOf(...) / MutableList<T>（預設即 ArrayList）

fun main() {
    // 建立不可變 List
    val list_data = listOf("aaa", "bbb", "ccc", "eeee")

    println("contents: $list_data")
    println("size of arrayList: ${list_data.size}")

    // 走訪 - for-each
    println(".....2......")
    for (str in list_data) {
        println(str)
    }

    // forEach() - 匿名函式
    println(".....3......")
    list_data.forEach(object : Consumer<String> {
        override fun accept(item: String) {
            println(item)
        }
    })
    println(".....4......")
    list_data.forEach(fun(item: String) {
        println(item)
    })

    // lambda 寫法
    println(".....5......")
    list_data.forEach { item ->
        println(item)
    }

    // lambda 簡化寫法
    println(".....6......")
    list_data.forEach {
        println(it)
    }

    // 方法參考語法
    println(".....7......")
    list_data.forEach(::println)//每個元素傳給 println


}
