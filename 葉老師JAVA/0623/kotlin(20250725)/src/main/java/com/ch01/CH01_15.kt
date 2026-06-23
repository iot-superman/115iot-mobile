package com.ch01

fun main() {
    var sum = 0  // 總和

    for (i in 1..10) {
        if (i % 2 == 0)  // 如果是偶數就略過
            continue
        sum += i  // 累加奇數
    }

    println("1-10奇數總和是 : $sum")
}