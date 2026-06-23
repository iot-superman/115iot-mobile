package com.ch04.CH04_07
//匿名陣列
fun add(nums: IntArray): Int {
    var sum = 0
    for (num in nums) {
        sum += num
    }
    return sum
}

fun main() {
    val matrix1 = intArrayOf(1, 2, 3, 4, 5)
    println(add(matrix1))

    val matrix2 = intArrayOf(1, 2, 3, 4, 5)
    println(add(matrix2))

    println(add(intArrayOf(1, 2, 3, 4, 5))) // 匿名陣列
}
