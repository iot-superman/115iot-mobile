package com.ch01

fun main() {
    val pwd = 70 // 密碼數字，可改為 (1..100).random() 產生隨機數

    while (true) {
        print("請猜1-100的數字 : ")
        val input = readLine()  // 從使用者讀取輸入（字串）
        val num = input?.toIntOrNull()  // 嘗試轉為 Int，若無法轉換則為 null

        if (num == null) {
            println("輸入無效，請輸入數字！")
            continue
        }

        if (num == pwd) {
            println("恭喜猜對了!!")
            break
        } else {
            println("猜錯了請再答一次!")
        }
    }
}