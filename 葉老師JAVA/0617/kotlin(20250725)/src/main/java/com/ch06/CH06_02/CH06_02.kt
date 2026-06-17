package com.ch06.CH06_02
//封裝+繼承
open class Tshape {
    private var upline: Double = 0.0
    private var downline: Double = 0.0
    private var height: Double = 0.0
    var Tx: Int = 0

    // 提供 Getter/Setter 方法
    fun setUpline(value: Double) {
        upline = value
    }

    fun getUpline(): Double {
        return upline
    }

    fun setDownline(value: Double) {
        downline = value
    }

    fun getDownline(): Double {
        return downline
    }

    fun setHeight(value: Double) {
        height = value
    }

    fun getHeight(): Double {
        return height
    }
}

class TshapeArea : Tshape() {
    var y: Double = 1.4

    fun area(): Double {
        // 後教
        Tx = 100
        println("在子類別內使用Tx=$Tx")
        y = 2.0
        println("在子類別內使用y=$y")
        // 先教
        return (getUpline() + getDownline()) * getHeight() / 2
    }
}
////////////////////////////////////////////////////////////////
//or
open class Tshape2 {
    // 私有實際欄位
    private var _upline: Double = 0.0
    private var _downline: Double = 0.0
    private var _height: Double = 0.0

    var Tx: Int = 0

    // Kotlin 風格屬性存取器
    var upline: Double
        get() = _upline
        set(value) {
            _upline = value
        }

    var downline: Double
        get() = _downline
        set(value) {
            _downline = value
        }

    var height: Double
        get() = _height
        set(value) {
            _height = value
        }
}

class TshapeArea2 : Tshape2() {
    var y: Double = 1.4

    fun area(): Double {
        Tx = 100
        println("在子類別內使用Tx=$Tx")
        y = 2.0
        println("在子類別內使用y=$y")
        return (upline + downline) * height / 2
    }
}

fun main() {
    val T = TshapeArea()
    T.setDownline(20.0)
    T.setHeight(33.0)
    T.setUpline(40.0)
    println("面積為: ${T.area()}")

    //////////////////////////////////////////////////
    // 物件後使用Tx
    T.Tx = 200
    println("外部Tx=${T.Tx}")

    // 物件後使用y
    T.y = 5.0
    println("外部y=${T.y}")
}
