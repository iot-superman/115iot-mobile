package com.ch04.CH04_01

const val text3: String = "ABC"
// const val 類別定義
// 只允許在 top-level 或 object
// 只支援 基本型別（如 String, Int）
// 適用於常數宣告（如標準常數定義）
class MyClass{
    var text: String = "ABC"  // 公開變數，預設可讀寫
    val text2: String = "ABC"
    // 可在 任何地方（class、function 等
    // 一般不可變變數
    fun clear() {
        text = ""
    }
}

fun main() {
    val a = MyClass()

    // 取值
    println("Text = ${a.text}")

    // 設定
    a.text = "DEF"
    println("Text = ${a.text}")

    // 使用方法
    a.clear()
    println("Text = ${a.text}")

    println("text3=${text3}")
}
