package com.ch05.CH05_07
// 封裝
class Student{
    private var sno: String = ""
    private var sname: String = ""
    private var score: Int = 0

    fun getSno(): String {
        return "我的學號是: $sno"
    }

    fun setSno(value: String) {
        sno = value
    }

    fun getSname(): String {
        return "$sname 先生，請多多指教"
    }

    fun setSname(value: String) {
        sname = value
    }

    fun getScore(): Int {
        return score
    }

    fun setScore(value: Int) {
        score = when {
            value < 0 -> 0
            value > 100 -> 100
            else -> value
        }
    }

    fun iam(): String {
        return "I am $sno:$sname score=$score"
    }
}
////////////////////////////////////////
// or
class Student2 {
    var sno: String = ""
        set(value) {
            field = value
        }
        get() = "我的學號是: $field"

    var sname: String = ""
        set(value) {
            field = value
        }
        get() = "$field 先生，請多多指教"

    var score: Int = 0
        set(value) {
            field = when {
                value < 0 -> 0
                value > 100 -> 100
                else -> value
            }
        }
    fun iam(): String {
        return "I am $sno:$sname score=$score"
    }
}

fun main() {
    val s1 = Student()
    print("請輸入您的學號：")
    val sno = readLine() ?: ""
    s1.setSno(sno)
    print("請輸入您的姓名：")
    val sname = readLine() ?: ""
    s1.setSname(sname)
    print("請輸入您的成績：")
    val score = readLine()?.toIntOrNull() ?: 0
    s1.setScore(score)
    println(s1.iam())
    println(s1.getSname())
    println(s1.getSno())
    println("成績為：${s1.getScore()}")
    //////////////////////////////////////////////////////
    val s2 = Student()
    print("請輸入您的學號：")
    val sno2 = readLine() ?: ""
    s2.setSno(sno2)
    print("請輸入您的姓名：")
    val sname2 = readLine() ?: ""
    s2.setSname(sname2)
    print("請輸入您的成績：")
    val score2= readLine()?.toIntOrNull() ?: 0
    s2.setScore(score2)
    println(s2.iam())
    println(s2.getSname())
    println(s2.getSno())
    println("成績為：${s2.getScore()}")
}
