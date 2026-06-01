/*

## 題目 1：九九乘法表 請撰寫一個 Kotlin 程式，印出 1 到 9 的九九乘法表。
 輸出格式範例:
 1 x 1 = 1
 1 x 2 = 2
 ...
 9 x 9 = 81

## 題目 2： input 三個不同的points 值，且值需小於110, 利用下列表格，取得對應的顏色
計算 karma value , 使用when 列印顏色
val karma = ( Math.pow(Math.random(), (110-points)/100.0) *20 ).toInt()

karma value     color
0-5				red
6-10			orange
11-15			purple
16-20			green

 */
fun main() {
     _9x9()
    println()
    println("請輸入三個不同的points值，且值需小於110：")
    var point1 : Int


    for (i in 1..3){
        var points : Int
        print("points$i: ")
        do {
            points  = readln().toInt()
            if (points >= 110){
                println("輸入錯誤，points值需小於110，請重新輸入：")
            }
        }while (points >=110)

        val karma = ( Math.pow(Math.random(), (110-points)/100.0) *20 ).toInt()
        val color = when(karma){
            in 0..5 -> "red"
            in 6..10 -> "orange"
            in 11..15 -> "purple"
            in 16..20 -> "green"
            else -> "unknown"
        }
        println("points: $points, karma: $karma, color: $color")
    }
}

fun _9x9(){
    for (i in 1..9){
        for (j in 1..9){
            println("$i x $j = ${i*j}")
        }
    }
}

fun getColor(points:Int):String{
    val karma = ( Math.pow(Math.random(), (110-points)/100.0) *20 ).toInt()
    return when(karma){
        in 0..5 -> "red"
        in 6..10 -> "orange"
        in 11..15 -> "purple"
        in 16..20 -> "green"
        else -> "unknown"
    }
}