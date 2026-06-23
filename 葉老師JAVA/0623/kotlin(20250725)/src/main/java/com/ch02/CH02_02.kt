package com.ch02

fun main() {
    // 三種宣告方式中的第三種，直接初始化二維陣列
    val x = arrayOf(
        intArrayOf(70, 80),
        intArrayOf(90, 95),
        intArrayOf(75, 73)
    )

    // 修改某個元素
    x[0][1] = 100

    // 手動印出
    println("${x[0][0]} ${x[0][1]}")
    println("${x[1][0]} ${x[1][1]}")
    println("${x[2][0]} ${x[2][1]}")

    var totalScore = 0

    for (row in x) {
        var singleScore = 0
        for (value in row) {
            print("$value ")
            singleScore += value
            totalScore += value
        }
        println()
        println("single score is: $singleScore")
    }
    println("total score is: $totalScore")
    println("...........................................")
    var totalScore2 = 0

    for (i in x.indices) {
        val row2 = x[i]
        var singleScore2 = 0

        for (j in row2.indices) {
            val value = row2[j]
            print("$value ")
            singleScore2 += value
            totalScore2 += value
        }

        println()
        println("single score is: $singleScore2")
    }

    println("total score is: $totalScore2")

}
