package com.ch01

fun main() {
    for (x in 0 until 2) {
        for (y in 0 until 4) {
            println("x=$x, y=$y")
        }
    }
    println("..............................")
    for (x in 0 until 2) {
        for (y in x until 4) {
            println("x=$x, y=$y")
        }
    }
    println("..............................")
    for (i in 1..9) {
        for (j in 1..9) {
            print("%d*%d=%2d  ".format(i, j, i * j))
        }
        println()
    }
}
