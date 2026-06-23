package com.ch09.CH09_04

import java.util.function.Consumer

fun main() {
    // 建立
    val mlist: MutableList<String> = mutableListOf()

    // add
    mlist.add("aaa")
    mlist.add("bbb")
    mlist.add("ccc")
    mlist.add(1, "eeee")  // 插入到 index 1

    println("contents: $mlist")
    println("size of arraylist: ${mlist.size}")

    // 修改
    mlist[0] = "ddd"
    println("contents: $mlist")

    // 刪除
     mlist.remove("bbb")
     println("contents: $mlist")
     mlist.removeAt(0)
     println("contents: $mlist")

    // foreach
    println(".....1......")
    for (str in mlist) {
        println(str)
    }

    // forEach() - 匿名函式
    mlist.forEach(object : Consumer<String> {
        override fun accept(t: String) {
            println(t)
        }
    })

    mlist.forEach(fun (t: String) {
            println(t)
    })

    // forEach 使用 lambda
    println(".....2......")
    mlist.forEach { t ->
        println(t)
    }

    // forEach lambda 簡寫
    println(".....4......")
    mlist.forEach { println(it) }

    // :: 語法
    println(".....5......")
    mlist.forEach(::println)
}
