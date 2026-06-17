package com.ch06.CH06_04
//繼承+super
open class Father {
    protected open val x: Int = 50
    open fun printInfo() {
        println(".....1.....")
    }
}

class Child : Father() {
    // 使用 override 遮蔽父類的屬性
    public override val x: Int = 100
    override fun printInfo() {
        println("父x = ${super.x}")
        println("子x = $x")
        super.printInfo()
    }
}

fun main() {
    val father = Father()
    val child = Child()

    // Kotlin 中，無法直接從外部存取 protected 成員，需透過函式或 public 屬性
    println("列印Father類別 x : 無法直接存取 protected")
    println("列印Child 類別 x : ${child.x}")
    child.printInfo()
}
