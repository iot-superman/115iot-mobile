package com.ch01
// if else if
// when ->
fun main() {
    val price:Any
    val ticket = 100 // 標準票價
    print("enter your age : ")
    val age = readLine()!!.toInt() // 讀取年齡資料

    // 大於等於80歲，小於等於6歲=>打2折
    // 79~60歲，12~7歲=>打5折
    // 其它不打折
    if (age >= 80 || age <= 6) {
        price = ticket * 0.2
        println("ticket price is: $price")
    } else if (age in 60..79 || age in 7..12) {
        price = ticket * 0.5
        println("ticket price is: $price")
    } else {
        price = ticket.toDouble()
        println("ticket price is: $price")
    }
    ////////////////////////////////////////////////////////
    val price2 = when {
        age >= 80 || age <= 6 -> ticket * 0.2
        age in 60..79 || age in 7..12 -> ticket * 0.5
        else -> ticket.toDouble()
    }
    println("ticket price is: $price2")

}