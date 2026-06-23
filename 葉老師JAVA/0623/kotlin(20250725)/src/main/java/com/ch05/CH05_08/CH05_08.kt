package com.ch05.CH05_08
// static 屬性
// 在kotlin 沒有真正的 static 關鍵字，可用companion object取代
class PrintSample {
    companion object {
        fun output1() {
            println("output1.....")
        }
    }
    fun output2() {
        println("output2.....")
    }
}
fun output3() {
    println("測試方法")
}

fun main() {
    PrintSample.output1() // 類別名稱呼叫 companion object 方法

    val obj = PrintSample()
    obj.output2()           // 呼叫實體方法
    output3()             // 直接呼叫頂層函式
}
