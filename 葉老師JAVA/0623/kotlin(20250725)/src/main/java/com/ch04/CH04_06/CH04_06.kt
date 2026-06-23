package com.ch04.CH04_06
// 傳物件時是用call by address說明
class DataBank {
    var x: Int = 0
    var y: Int = 0
}

fun swap(b: DataBank) {
    val tmp = b.x
    b.x = b.y
    b.y = tmp
    println("swap方法內部   x = ${b.x},  y = ${b.y}")
}

fun main() {
    val a = DataBank()
    a.x = 10
    a.y = 20
    println("呼叫swap方法前 x = ${a.x},  y = ${a.y}")
    swap(a)
    println("呼叫swap方法後 x = ${a.x},  y = ${a.y}")
}
