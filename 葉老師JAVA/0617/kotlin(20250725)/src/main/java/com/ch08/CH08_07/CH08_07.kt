package com.ch08.CH08_07

// 定義介面
interface School {
    val num_s: Int
        get() = 100

    fun demo() {
        println("School")
    }

    fun test1() {
        println("test1..")
    }

    fun test2()
    fun test3()
}

// 實作 School 的類別 Department
class Department : School {
    val num_D = 200

    override fun test2() {
        println("test2..")
    }

    override fun test3() {
        println("test3..")
    }

    fun test4() {
        println("test4..")
    }

    // 覆寫 demo（介面中為預設方法）
    override fun demo() {
        println("Department")
    }
}

fun main() {
    println("....new Department class 子....")
    val A = Department()

    A.demo()
    A.test1()
    A.test2()
    A.test3()
    A.test4()
    println(A.num_s)
    println(A.num_D)

    println("....Upcasting....")
    // 向上轉型為介面型別
    val B: School = Department()

    B.demo()
    B.test1()
    B.test2()
    B.test3()
    println("num_s: ${B.num_s}")
    // B.test4() // ❌ 無法使用，型別是 School，不包含 test4()

    println("....Downcasting....")
    val C = B as Department // 明確轉回子類別

    C.demo()
    C.test1()
    C.test2()
    C.test3()
    C.test4()
    println(C.num_s)
    println(C.num_D)
}
