package com.ch05.CH05_06

class Student {
    var sno: String = ""
    var sname: String = ""
    var score: Int = 0
    fun iam() {
        println("I am $sno:$sname score=$score")
    }
}

fun main() {
    val s1 = Student()
    s1.sno = "1001"
    s1.sname = "JOHN"
    s1.score = 500  // ✅ 目前沒有限制
    s1.iam()        // 輸出: 500
}
