package com.ch01

fun main() {
    var i = 1
    var j = 1
    do {
        do {
            print("%d*%d=%2d  ".format(i, j, i * j))
            j++
        } while (j <= 9)
        i++
        j = 1
        println()
    } while (i <= 9)
}
