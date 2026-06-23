package com.ch02
// array
fun main() {
    // 原始寫法
    val deg1 = 25.0
    val deg2 = 22.0
    val deg3 = 24.0
    val deg4 = 20.0
    val average1 = (deg1 + deg2 + deg3 + deg4) / 4
    println("一週平均溫度 : %.2f".format(average1))

    // 使用陣列的三種宣告
    ///////////////////////////////////////////////////////
    // 第一種宣告
    // val degree2 = DoubleArray(4)

    // 第二種宣告（可逐個指定）
    // val degree2 = DoubleArray(4)
    // degree2[0] = 25.0
    // degree2[1] = 22.0
    // degree2[2] = 24.0
    // degree2[3] = 20.0

    // 第三種，直接初始設定
    val degree2 = doubleArrayOf(25.0, 22.0, 24.0, 20.0)
    ///////////////////////////////////////////////////////

    // 修改
    degree2[2] = 100.0

    // 取值
    println(degree2[0])
    println(degree2[1])
    println(degree2[2])
    println(degree2[3])

    // 使用 for-each (Kotlin for-in)
    var total2 = 0.0
    for (element in degree2) {
        println(element)
        total2 += element
    }
    // 使用indices
    for (i in degree2.indices) {
        println(degree2[i])
        total2 += degree2[i]
    }

    val average2 = total2 / degree2.size
    println("一週平均溫度 : %.2f".format(average2))
}
