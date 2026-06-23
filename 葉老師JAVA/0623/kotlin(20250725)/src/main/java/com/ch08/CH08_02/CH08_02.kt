package com.ch08.CH08_02

// 介面+介面的屬性
interface Shape{
    companion object {
        const val PI = 3.14  // Kotlin 中 interface 常數應放在 companion object
    }
    fun area(): Double
}

// 定義 Rectangle 實作 Shape
class Rectangle(private val height: Double, private val width: Double) : Shape {
    override fun area(): Double {
        return height * width
    }
}

// 定義 Circle 實作 Shape
class Circle(private val r: Double) : Shape {
    override fun area(): Double {
        return Shape.PI * r * r
    }
}

fun main() {
    val rectangle = Rectangle(2.0, 3.0)
    val circle = Circle(2.0)

    println("矩形面積     : ${rectangle.area()}")
    println("rectangle.PI : ${Shape.PI}")  // 介面常數使用 Shape.PI

    println("circle.PI    : ${Shape.PI}")  // 介面常數使用 Shape.PI
    println("圓面積       : ${circle.area()}")

    // Shape.PI = 5.0 // ❌ 不可修改常數，會編譯錯誤
}
