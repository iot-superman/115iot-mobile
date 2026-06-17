package com.ch01
// if else
fun main() {
    print("you age : ")
    val age = readLine()?.toIntOrNull() ?: 0 // 讀取成績資料，若輸入錯誤自動當0分
    if (age < 20) {
        println("Your age is too small")
    } else {
        println("Welcome!")
    }

    if (age < 20)
        println("Your age is too small")
    else
        println("Welcome!")

    ////////////////////////////////////////////////
    print("請輸入任意整數 : ")
    val x = readLine()?.toIntOrNull() ?: 0
    // 從使用者輸入讀取一行文字（readLine()）
    // 嘗試將該文字轉成整數（toIntOrNull()）：
    // 若轉換成功，就將整數值賦值給變數 x。
    // 若轉換失敗（例如輸入非數字文字），則回傳 null。
    // 使用 Elvis 運算子 ?: 來處理 null 的情況：
    // 如果轉換結果是 null，則預設使用 0 作為 x 的值。

    if (x >= 0)
        println("絕對值是$x")
    else
        println("絕對值是${-x}")

}