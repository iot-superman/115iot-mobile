package com.ch06.CH06_01
// 繼承
// 在Kotlin 中，open 是一個關鍵字，用來允許某個類別或成員（函式或屬性）被子類別覆寫（override）。這是 Kotlin 和 Java 之間一個重要的差異：
// 在Java 中，所有類別和方法預設是「可繼承、可覆寫」的（除非你加上 final）。
// 但在Kotlin 中，所有類別與方法預設是 final（不可被繼承或覆寫），要想讓它們可被繼承或覆寫，就必須明確加上 open。
open class Emp {
    var salary: Int = 0

    open fun showSal() {
        println(salary)
    }
}

class Manager : Emp() {
    var bonus: Int = 0

    fun showBonus() {
        println(bonus)
    }
    override fun showSal() {
        println(salary + bonus)
    }
}

fun main() {
    val mary = Manager()
    mary.salary = 30000
    mary.bonus = 30000
    mary.showBonus()
    mary.showSal()
}
