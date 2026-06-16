fun main() {
    println("1.")
    // 建立第 1 位學生資料
    val std1 = Student()
    std1.name = "Tom"
    std1.age = 18
    std1.score = 85

    // 建立第 2 位學生資料
    val std2 = Student()
    std2.name = "Amy"
    std2.age = 17
    std2.score = 92

    // 建立第 3 位學生資料
    val std3 = Student()
    std3.name = "Bob"
    std3.age = 19
    std3.score = 55

    // 呼叫每位學生的 printInfo() 函式
    std1.printInfo()
    std2.printInfo()
    std3.printInfo()
//----------------------------
println("2.")
// 建立第 1 個矩形物件
    val rect1 = Rectangle()
    rect1.width = 120
    rect1.height = 30

    // 建立第 2 個矩形物件
    val rect2 = Rectangle()
    rect2.width = 250
    rect2.height = 60

    // 列印矩形 1 的資訊
    println("--- 矩形 1 ---")
    println("寬：${rect1.width}")
    println("高：${rect1.height}")
    println("面積：${rect1.area()}")
    println("周長：${rect1.perimeter()}")

    // 列印矩形 2 的資訊
    println("--- 矩形 2 ---")
    println("寬：${rect2.width}")
    println("高：${rect2.height}")
    println("面積：${rect2.area()}")
    println("周長：${rect2.perimeter()}")
}


class Student() {
    // 宣告屬性並給予預設值，讓 Kotlin 知道型別
    var name: String = ""
    var age: Int = 0
    var score: Int = 0

    // 判斷是否及格
    fun isPass(): Boolean = score >= 60


    // 列印學生完整資訊
    fun printInfo() {
        // 依據 isPass() 的結果決定顯示 "是" 或 "否"
        val passStatus = if (isPass()) "是" else "否"

        // 修正字串格式，將所有內容與變數放入引號內
        println("姓名：$name，年齡：$age，成績：$score，及格：$passStatus")
    }
}

/*
題目 2：計算面積和周長

建立 矩形(Rectangle) 類別：
  * 屬性：寬（width）、高（height）
  * 包含兩個方法：計算面積 area()，計算周長 perimeter()

請在主程式中建立 2 個矩形物件，資料如下：

矩形		寬 width		高 height
矩形 1	120			30
矩形 2	250			60

並分別列印每個矩形的：
寬
高
面積
周長
 */

class  Rectangle(){
    var width: Int =0
    var height : Int=0
    fun area() : Int {
        return width*height
    }
    fun perimeter(): Int{
        return (width+height)*2
    }
}

