package com.ch01

fun main(){
    val sum1 = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10;
    println("總和 = $sum1")

    // 1+2+3+4+....+10
    var sum2 = 0
    var i = 1
    while (i <= 10) {
        sum2 += i
        i++
    }
    println("總和 = $sum2")

    // 1+3+5+7+9+11
    var sum3 = 0
    i = 1
    while (i <= 11) {
        sum3 += i
        i += 2
    }
    println("總和 = $sum3")

    // 11+9+7+5+3+1
    var sum4 = 0
    i = 11
    while (i >= 1) {
        sum4 += i
        i -= 2
    }
    println("總和 = $sum4")
}