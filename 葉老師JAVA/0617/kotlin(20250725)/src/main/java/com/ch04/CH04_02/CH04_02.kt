package com.ch04.CH04_02

class Student {
    var sno: String = ""
    var sname: String = ""

    fun iam() {
        println("I am $sno:$sname 3Q")
    }
}

fun main() {
    val s1 = Student()
    s1.sno = "1001"
    s1.sname = "JOHN"
    s1.iam()

    val s2 = Student()
    s2.sno = "1002"
    s2.sname = "MARY"
    s2.iam()
}
