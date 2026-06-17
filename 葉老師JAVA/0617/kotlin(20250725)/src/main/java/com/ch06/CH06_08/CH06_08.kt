package com.ch06.CH06_08
//向上轉型、向下轉型
open class School{
    val numS = 100

    open fun demo() {
        println("School")
    }

    fun test1() {
        println("test1")
    }

    fun test2() {
        println("test2")
    }
}

class Department : School() {
    val numD = 200

    override fun demo() {
        println("Department")
    }

    fun test3() {
        println("test3")
    }
}

fun main() {
    println("....new School class 父....")
    val A = School()
    A.demo()
    A.test1()
    A.test2()
    println(A.numS)

    println("....new Department class 子....")
    val B = Department()
    B.demo()
    B.test1()
    B.test2()
    B.test3()
    println(B.numS)
    println(B.numD)

    println("....Upcasting....")

    //（Upcasting）
    val C1: School = Department()
    C1.demo()
    C1.test1()
    C1.test2()
    println(C1.numS)
    // println(C1.numD) // ❌ 無法存取
    // C1.test3() // ❌ 無法存取

    println("....Downcasting....")
    // 若需要呼叫子類別的方法，需做 downcast（注意安全）

//    val C = B as com.ch08.Department // 明確轉回子類別
    val D1 = C1 as Department
    println("Downcasting to Department:")
    println(D1.numS)
    println(D1.numD)
    D1.demo()
    D1.test1()
    D1.test2()
    D1.test3()
}
