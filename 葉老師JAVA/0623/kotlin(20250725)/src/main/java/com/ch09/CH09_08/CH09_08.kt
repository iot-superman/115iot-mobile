package com.ch09.CH09_08

import java.util.function.BiConsumer

data class Phone(var pname: String, var price: Int)

fun main() {
    val map1 = mutableMapOf<String, Phone>(
        "IPHONE" to Phone("哀鳳", 300000),
        "SAMSUNG" to Phone("三星", 200000),
        "OPPO" to Phone("歐珀", 100000)
    )

    // 修改（HTC 不存在會報錯，需判斷）
    map1["IPHONE"]?.price = 50000  // Kotlin風格，安全呼叫

    // 刪除
//    p.remove("IPHONE")

    // 取得資料
    println(map1["IPHONE"]?.pname)
    println(map1["IPHONE"]?.price)
    println(".............1................")
    // 走訪 by for
    println("---- 用 for 走訪 ----")
    for ((key, value) in map1) {
        println("$key ${value.pname} ${value.price}")
    }
    println(".............2................")
    ///////////////////////////////////////////////////////
    // forEach() - 匿名函式
    map1.forEach(object :BiConsumer<Any, Phone>{
        override fun accept(k: Any, v: Phone) {
            println("$k ${v.pname} ${v.price}")
        }
    })

    println(".............3................")
    map1.forEach(fun (k: Any, v: Phone) {
            println("$k ${v.pname} ${v.price}")
    })

    println(".............4................")
    // lambda
    println("---- 用 lambda 走訪 ----")
    map1.forEach { (k, v) ->
        println("$k ${v.pname} ${v.price}")
    }
    println(".............5................")
    map1.forEach {
        val k = it.key
        val v = it.value
        println("$k ${v.pname} ${v.price}")
    }

    println(".............6................")
    map1.entries.forEach(::println)

}