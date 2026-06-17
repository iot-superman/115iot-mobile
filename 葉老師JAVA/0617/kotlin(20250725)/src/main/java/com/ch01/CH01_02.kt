package com.ch01
// var vs vale
fun main() {
//    var num1: Int = 4
//    var num2: Double = 5.5
//    var num3: Int = 6
//    var num4: Double = 7.2
//    var str1: String = "Hello"
//    var str2: String = "World"
    /////////////////////////////////////
    var num1 = 4
    var num2 = 5.5
    var num3 = 6
    var num4 = 7.2
    var str1 = "Hello"
    var str2: String = "World"
    print("num1: $num1, Age: $num2 \n")
    println("num1: $num3, Age: $num4")
    println("num1+num2: ${num1+num2}")
    println("result:$str1 $str2")
    ////////////////////////////////
    val number = 100 //常數、final
    //number = 101//false
    println("number: $number")
}