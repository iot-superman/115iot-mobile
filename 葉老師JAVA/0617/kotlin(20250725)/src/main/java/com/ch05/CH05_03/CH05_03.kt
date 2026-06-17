package com.ch05.CH05_03
//建構方法說明+overload
class MyClass {
    var age: Int = 0
    var name: String = ""

    constructor(a: Int) {
        age = a
    }
    constructor(str: String) {
        name = str
    }
    constructor(a: Int, str: String) {
        age = a
        name = str
    }
    fun printInfo() {
        println(name)
        println(age)
    }
}
//or
class MyClass2(var age: Int = 0, var name: String = "") {
    fun printInfo() {
        println(name)
        println(age)
    }
}

fun main() {
    val a = MyClass(20)

    val b = MyClass2(name = "John")
    b.printInfo()

    val c = MyClass2(25, "Lin")
    c.printInfo()

    ////////////////////////////////////
    val obj1 = MyClass2(20)
    //or
    val obj2 = MyClass2(age=20)
    a.printInfo()

    val obj3 = MyClass2(name = "John")
    b.printInfo()

    val obj4 = MyClass2(25, "Lin")
    c.printInfo()
}
