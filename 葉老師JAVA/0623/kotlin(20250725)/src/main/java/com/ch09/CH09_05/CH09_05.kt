package com.ch09.CH09_05

import java.util.function.Consumer

//宣告data，::println 就會自動印出
data class Student(var no: Int, var sname: String, var score: Int)

data class Student2(
    var no: Int,
    var sname: String,
    var score: Int
) : Comparable<Student> {
    override fun compareTo(other: Student): Int {
        return this.no.compareTo(other.no) // 根據學號排序
    }
}

class Student3(var no: Int, var sname: String, var score: Int)

///////////////////////////////////////////////////////////////////////////
// data class Student(val no: Int, val sname: String, val score: Int)
// println(Student(1, "John", 90)) → 自動轉為呼叫 toString()
//→ Student(no=1, sname=John, score=90)

// class Student(val no: Int, val sname: String, val score: Int)
// println(Student(1, "John", 90)) → 預設 Object.toString()
// → Student@5e91993f
///////////////////////////////////////////////////////////////////////////


fun main() {
    // 建立並新增資料
    val students = mutableListOf(
        Student(1, "JOHN", 100),
        Student(8, "MARY", 80),
        Student(4, "TOM", 90)
    )

    // 更新
    students[0].sname = "ccccccc"
    // 刪除
//    if (students.size > 2) {
//        students.removeAt(2)
//    }

    // 走訪
    println("......1......")
    for (student in students) {
        println("${student.no}:${student.sname}:${student.score}")
    }
    println("......2......")
    // forEach() - 匿名函式
    students.forEach(object : Consumer<Student> {
        override fun accept(t: Student) {
            println(t)
        }
    })

    println("......3......")
    students.forEach(fun (t: Student) {
            println(t)
    })

    println("......4......")
    // 改成 lambda
    students.forEach { student ->
        println("${student.no} ${student.sname} ${student.score}")
    }

    println("......5......")
    students.forEach {
        println("${it.no}:${it.sname}:${it.score}")
    }

    println("......6......")
    // 修改，以姓名為條件
    students.forEach {
        if (it.sname == "MARY") {
            it.score = 30
        }
    }
    students.forEach(::println) //class要設成data class才會顯示值


}