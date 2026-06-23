package com.ch04.CH04_08
//補充，物件匿名宣告，傳入function
class TaipeiBank {
    var account: Int = 1001    // 帳號
    var balance: Int = 100000  // 存款金額
}

fun printInfo(tb: TaipeiBank) {
    println("帳戶 : ${tb.account}, 餘額 : ${tb.balance}")
}

fun main() {
    val t = TaipeiBank()
    printInfo(t)
    printInfo(TaipeiBank())
    printInfo(TaipeiBank())
}
