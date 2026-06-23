package com.ch09.CH09_06

import java.util.function.BiConsumer

fun main() {
    // 建立不可變 Map
    val map = mapOf(
        105 to "明新科大",
        102 to "中央大學",
        103 to "中原大學",
        104 to "清華大學"
    )
    println("....1.....")
    println("Map內容 : $map")

    // 走訪
    println("....2.....")
    for ((key, value) in map) {
        println("$key $value")
    }

    // 取值
    println("....3.....")
    println(map[104])

    // 其他操作
    println("....4.....")
    println("Map元素個數 : ${map.size}")
    println("Map是空的   : ${map.isEmpty()}")
    println("Map包含101  : ${map.containsKey(101)}")

    // 建立第二個 mapOf
    val map2 = mapOf(
        "a101" to "marry",
        "a102" to "bill",
        "a103" to "natasha",
        "a104" to "joe"
    )
    println("....5.....")
    for ((key, value) in map2) {
        println("$key $value")
    }

    // 建立混合型 map (Any, Any)
    val map3 = mapOf<Any, Any>(
        "a101" to "marry",
        "a102" to "bill",
        "a103" to "natasha",
        "a104" to 444
    )
    println("....6.....")
    map3.forEach { (key, value) ->
        println("$key $value")
    }
    /////////////////////////////////////////////////
    println("....7.....")
    // forEach() - 匿名函式
    map3.forEach(object : BiConsumer<Any, Any> {
        override fun accept(t: Any, u: Any) {
            println("$t $u")
        }
    })
    println("....8.....")
    map3.forEach(fun (t: Any, u: Any) {
            println("$t $u")
    })

    // 改成 lambda
    println("....9.....")
    map3.forEach { (key, value) ->
        println("$key $value")
    }
    println("....10.....")
    map3.forEach {
        println("${it.key}:${it.value}")
    }
    println("....11.....")
    map3.forEach(::println)

}
