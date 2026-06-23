package com.ch09.CH09_07

import java.util.function.BiConsumer

fun main() {

// 建立可變 Map
    val map = mutableMapOf(
        105 to "明新科大",
        102 to "台灣科大",
        103 to "台北科大",
        104 to "清華大學"
    )
    println("....1.....")
    println("Map內容     : $map")
// 修改
    map[101] = "aaaaa"
    println("....2.....")
    println("HashMap內容     : $map")

// 刪除
    map.remove(103) // 刪除鍵值103
//    map.clear() // 刪除所有元素
    println("....3.....")
    println("Map內容     : $map")

// 取值
    println("....4.....")
    println(map[104])

// 走訪 (forEach)
    println("....5.....")
    map.forEach { (key, value) ->
        println("$key $value")
    }

///////////////////////////
    val map2 = mutableMapOf(
        "a101" to "marry",
        "a102" to "bill",
        "a103" to "natasha",
        "a104" to "joe"
    )

    println("....6.....")
    map2.forEach { (key, value) ->
        println("$key $value")
    }

// 其它功能
    println("....7.....")
    println("HashMap元素個數 : ${map.size}")
    println("HashMap是空的   : ${map.isEmpty()}")
    println("HashMap包含101  : ${map.containsKey(101)}")

// 可以存放任何型別 (Object 相當於 Any)
    val map3 = mutableMapOf<Any, Any>(
        "a101" to "marry",
        "a102" to "bill",
        "a103" to "natasha",
        "a104" to 444
    )
    println("....8.....")
    map3.forEach { (key, value) ->
        println("$key $value")
    }
    ///////////////////////////////////////////
    // forEach() - 匿名函式
    map3.forEach(object : BiConsumer<Any, Any> {
        override fun accept(t: Any, u: Any) {
            println("$t $u")
        }
    })
    println("....9.....")
    map3.forEach(fun (t: Any, u: Any) {
        println("$t $u")
    })

    // 改成 lambda
    println("....10.....")
    map3.forEach { (key, value) ->
        println("$key $value")
    }
    println("....11.....")
    map3.forEach {
        println("${it.key}:${it.value}")
    }
    println("....12.....")
    map3.forEach(::println)

}