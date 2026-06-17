package com.ch01

fun main() {
    var i = 1
    var j = 1
    while (i <= 9) {
        while (j <= 9) {
            print("%d*%d=%2d  ".format(i, j, i * j))
            j++
        }
        println()
        i++
        j = 1
    }
}