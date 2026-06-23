package com.ch04.CH04_09
//類別內屬性為物件說明
class Student {
    var name: String = ""
    var eng: Int = 0
    var math: Int = 0
}

class Show {
    val x = Student() // 屬性 (Student 物件)
    
    fun showData() {
        println("姓名: ${x.name}")
        println("英文: ${x.eng}")
        println("數學: ${x.math}")
        println()
    }
}

fun main() {
    val b = Show()
    b.x.name = "Tom"
    b.x.eng = 100
    b.x.math = 99
    b.showData()
}
