package com.ch01

fun main() {
    val asum1 = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10;
    println("總和1 = $asum1")

    //1+2+3+4+....+10
    val asum2 = (1..10).sum()
    println("總和2 = $asum2")

    val asum2_2 = (1 until 11).sum()
    println("總和2_2 = $asum2_2")

    //1+3+5+7+9+11
    val asum3 = (1..11 step 2).sum()
    println("總和3 = $asum3")

    val asum3_2 = (1 until 12 step 2).sum()
    println("總和3_2 = $asum3_2")

    //11+9+7+5+3+1
    val asum4 = (11 downTo 1 step 2).sum()
    println("總和4 = $asum4")
    ////////////////////////////////////////////////////////////////
    // 1 + 2 + 3 + ... + 10
    var sum2 = 0
    for (i in 1..10) {
        sum2 += i
    }
    println("總和2 = $sum2")

    var sum2_2 = 0
    for (i in 1 until 11) {
        sum2_2 += i
    }
    println("總和2_2 = $sum2_2")

    // 1 + 3 + 5 + 7 + 9 + 11
    var sum3 = 0
    for (i in 1..11 step 2) {
        sum3 += i
    }
    println("總和3 = $sum3")

    var sum3_2 = 0
    for (i in 1 until 12 step 2) {
        sum3_2 += i
    }
    println("總和3_2 = $sum3_2")

    // 11 + 9 + 7 + 5 + 3 + 1
    var sum4 = 0
    for (i in 11 downTo 1 step 2) {
        sum4 += i
    }
    println("總和4 = $sum4")

}