package com.ch05.CH05_07_02
//封裝
data class Student(var sno:String, var sname:String, var score:Int)
fun main() {
    print("請輸入您的學號：")
    val sno = readLine() ?: ""

    print("請輸入您的姓名：")
    val sname = readLine() ?: ""

    print("請輸入您的成績：")
    val score = readLine()?.toIntOrNull() ?: 0

    val s1 = Student(sno,sname,score)

    println(s1.sno)
    println(s1.sname)
    println(s1.score)
    println("學號為:${s1.sno}," +
            "姓名為:${s1.sname}," +
            "成績為:${s1.score}")
}