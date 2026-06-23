package com.ch01
// if else if else
// when -> else
fun main() {
    print("請輸入分數 : ")
    val score = readLine()?.toIntOrNull() ?: 0 // 讀取成績資料，若輸入錯誤自動當0分
    if (score >= 90)
        println("A")
    else if (score >= 80)
        println("B")
    else if (score >= 70)
        println("C")
    else if (score >= 60)
        println("D")
    else
        println("F")
    //////////////////////////////////////////////////////
    print("請輸入分數 : ")
    val score2 = readLine()?.toIntOrNull() ?: 0 // 讀取成績資料，若輸入錯誤自動當0分

    when {
        score2 >= 90 -> println("A")
        score2 >= 80 -> println("B")
        score2 >= 70 -> println("C")
        score2 >= 60 -> println("D")
        else -> println("F")
    }

}