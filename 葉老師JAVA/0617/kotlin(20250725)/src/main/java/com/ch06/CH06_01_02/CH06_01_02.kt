package com.ch06.CH06_01_02
//繼承+override
open class Emp {
    // 保留和 Java 相同的名稱與用法：直接可存取 Salary
    var Salary: Int = 0

    open fun set_salary(Salary: Int) {
        this.Salary = if (Salary > 40000) 40000 else Salary
    }

    open fun ShowSal() {
        println(Salary)
    }
}

class Manager : Emp() {
    var Bonus: Int = 0

    override fun set_salary(Salary: Int) {
        // Manager 上限 60000
        this.Salary = if (Salary > 60000) 60000 else Salary
    }

    override fun ShowSal() {
        println(Salary + Bonus)
    }
}

fun main() {
    val John = Emp()
    John.set_salary(50000)
    John.ShowSal()
    John.Salary = 100
    John.ShowSal()

    val Mary = Manager()
    Mary.set_salary(70000)
    Mary.Bonus = 30000
    Mary.ShowSal()
}