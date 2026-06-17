package com.ch04.CH04_05
// this說明
class TaipeiBank {
    var account: Int = 0      // 帳號
    var balance: Int = 0      // 存款金額

    fun saveMoney(balance: Int) {  // 存款
        this.balance += balance
    }

    fun printInfo() {  // 列印帳號與餘額
        println("帳戶 : $account, 餘額 : $balance")
    }
}

fun main() {
    val a = TaipeiBank()      // 類別物件
    a.account = 10000001      // 設定帳號
    a.balance = 0             // 最初化存款是 0

    a.printInfo()             // 存款前
    a.saveMoney(100)          // 存款 100
    a.printInfo()             // 存款後
}
