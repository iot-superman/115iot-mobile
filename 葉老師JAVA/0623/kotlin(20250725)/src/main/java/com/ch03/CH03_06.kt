package com.ch03

fun main() {
    val score = intArrayOf(9, 14, 6, 18, 2, 10)
    findLargest(score)

    val matrix = arrayOf(
        intArrayOf(18, 32, 65, 27, 30),
        intArrayOf(17, 56, 12, 66)
    )
    findLargestInMatrix(matrix)
}

// 找出一維陣列中最大值
fun findLargest(arr: IntArray) {
    var max = arr[0]
    for (i in 1 until arr.size) {
        if (arr[i] > max) {
            max = arr[i]
        }
    }
    println("largest num = $max")
}

// 找出二維陣列中最大值
fun findLargestInMatrix(arr: Array<IntArray>) {
    var max = Int.MIN_VALUE
    for (row in arr) {
        for (value in row) {
            print("$value ")
            if (value > max) {
                max = value
            }
        }
        println()
    }
    println("largest num = $max")
}
