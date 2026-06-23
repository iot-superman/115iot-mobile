package com.ch03

//fun circleArea(r: Double): Double {
//    return Math.PI * r * r
//}

fun circleArea(r: Double): Double  = Math.PI * r * r

//fun rectangleArea(l: Double, w: Double): Double {
//    return l * w
//}

fun rectangleArea(l: Double, w: Double): Double = l * w

fun main() {
    println("CircleArea= ${circleArea(1.0)}")       // 加了前綴字串
    println("RectangleArea= ${rectangleArea(2.0, 1.0)}")
}
