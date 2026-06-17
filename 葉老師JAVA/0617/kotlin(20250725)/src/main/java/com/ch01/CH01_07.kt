package com.ch01
// if else
// try catch
fun main() {
    print("請輸入一個數字或 q/Q 離開: ")
    var str: String? = readLine()
    //readLine() 的回傳型別是 String?（可能是字串，也可能是 null），因為使用者可能按了 Enter 而沒有輸入。
    //ignoreCase:用來指定是否忽略大小寫
    if (str.equals("Q", ignoreCase = true)) {
        println("離開")
    } else {
        try {
            val num = str?.toIntOrNull() ?: 0 // null 或格式錯誤時用預設值
            println(num)
        } catch (e: NumberFormatException) {
            println("請輸入 q 或 Q")
        }
    }
}