package com.ch05.CH05_01
//建構方法說明
class TaipeiBank {
    var balance: Int

    // 初始區塊，等同建構子內容
    init {
        balance = 100  // 存款餘額初值是100
    }

    fun printBalance() {
        println("存款餘額 : $balance")
    }
}

fun main() {
    val a = TaipeiBank()  // 類別物件
    a.printBalance()      // 列印存款餘額
}
