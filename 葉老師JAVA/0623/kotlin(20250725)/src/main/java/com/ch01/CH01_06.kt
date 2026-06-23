package com.ch01
// when ->
fun main() {
    print("請輸入分數(0~100): ")
    val score = readLine()!!.toInt() // 讀取成績資料

    when (score / 10) { // 取整數後判斷
        9 -> println("A")
        8 -> println("B")
        7 -> println("C")
        6 -> println("D")
        else -> println("F")
    }
}