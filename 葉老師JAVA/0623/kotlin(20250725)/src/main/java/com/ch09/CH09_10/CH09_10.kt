package com.ch09.CH09_10

data class Student9_10(val no: Int, val sname: String, val score: Int)
//list<Student> 轉成 list<Map>
fun main() {
    val students = listOf(
        Student9_10(1, "JOHN", 100),
        Student9_10(8, "MARY", 80),
        Student9_10(4, "TOM", 90)
    )

    // 走訪學生列表
    println("........1.........")
    students.forEach {
        println("${it.no} ${it.sname} ${it.score}")
    }
    println("........2.........")
    students.forEach { student ->
        println("${student.no} ${student.sname} ${student.score}")
    }
    //////////////////////////////////////////
    // list<Student> 轉成 list<Map>
    println("........3.........")
    val items = mutableListOf<Map<String, Any>>()
    //原始寫法
    for (student in students) {
        val map = mutableMapOf<String,Any>()
        map.put("no", student.no)
        map.put("name", student.sname)
        map.put("score", student.score)
        items.add(map)
    }
    print(items)
    println("........4.........")
    //利用
//    for (student in students) {
//        val item = mapOf(
//            "no" to student.no,
//            "name" to student.sname,
//            "score" to student.score
//        )
//        items.add(item)
//    }
    println("........5.........")
    //使用匿名
//    for (student in students) {
//        items.add(mapOf(
//            "no" to student.no,
//            "name" to student.sname,
//            "score" to student.score
//        ))
//    }
    ////////////////////////////////////////////////////////////////
    println("........6.........")
    print(items)

    println("........7.........")
    for (item in items) {
        for ((key, value) in item) {
            print("$key: $value ")
        }
        println()
    }
    println("........6.........")
    // lambda
    items.forEach { item ->
        item.forEach { (key, value) ->
            print("$key: $value ")
        }
        println()
    }
    println("........7.........")
    items.forEach { it.forEach { (k, v) -> print("$k: $v ") }; println() }



}
