package com.ch05.CH05_05

//封裝
class Student {
    var sno: String = ""
    var sname: String = ""
    var age: Int = 18
    private var money: Int = 100

    fun iam() {
        println("I am $sno: $sname 3Q")
        money = 120  // 可以在類別內部修改
        println("I have ${money} dollars")
    }
}
fun main() {
    val s1 = Student()
    s1.sno = "1001"
    s1.sname = "JOHN"
    s1.iam()
    println("Age=${s1.age}")  // OK

    // ↓ 以下兩行都會產生錯誤，因為 money 是 private：
    // println("Money=${s1.money}")    // ❌ 不可存取
    // s1.money = 10000                // ❌ 不可設定

}
