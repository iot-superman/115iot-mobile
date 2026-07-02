import kotlin.math.PI

// ==================== 題目 1：圖形面積 ====================
open class Shape {
    open fun area(): Double {
        return 0.0
    }
}

class Circle(val radius: Double) : Shape() {
    override fun area(): Double {
        return PI * radius * radius
    }
}

class Rectangle(val width: Double, val height: Double) : Shape() {
    override fun area(): Double {
        return width * height
    }
}

// ==================== 題目 2：員工薪資 ====================
open class Employee {
    open fun calculateSalary(): Double {
        return 0.0
    }
}

class FullTimeEmployee(
    val name: String,
    val monthlySalary: Double
) : Employee() {
    override fun calculateSalary(): Double {
        return monthlySalary * 12
    }
}

class PartTimeEmployee(
    val name: String,
    val hourlyRate: Double,
    val workingHour: Int
) : Employee() {
    override fun calculateSalary(): Double {
        return hourlyRate * workingHour
    }
}

// ==================== 主程式 ====================
fun main() {
    // ------ 測試題目 1：圖形面積 ------
    println("=== 題目 1：圖形面積計算 ===")

    print("請輸入圓形的半徑: ")
    val radiusInput = readln().toDouble()
    val circle: Shape = Circle(radiusInput)

    print("請輸入矩形的寬度: ")
    val widthInput = readln().toDouble()
    print("請輸入矩形的高度: ")
    val heightInput = readln().toDouble()
    val rectangle: Shape = Rectangle(widthInput, heightInput)

    // 印出計算結果
    println("圓形面積為: ${String.format("%.2f", circle.area())}")
    println("矩形面積為: ${String.format("%.2f", rectangle.area())}")


    println("\n-------------------------------------------\n")


    // ------ 測試題目 2：員工薪資 ------
    println("=== 題目 2：員工薪資計算 ===")

    // 輸入正職員工資料
    print("請輸入正職員工姓名: ")
    val ftName = readln()
    print("請輸入 $ftName 的月薪: ")
    val ftSalary = readln().toDouble()
    val emp1: Employee = FullTimeEmployee(ftName, ftSalary)

    // 輸入兼職員工資料
    print("請輸入兼職員工姓名: ")
    val ptName = readln()
    print("請輸入 $ptName 的時薪: ")
    val ptRate = readln().toDouble()
    print("請輸入 $ptName 的工作時數: ")
    val ptHours = readln().toInt()
    val emp2: Employee = PartTimeEmployee(ptName, ptRate, ptHours)

    // 印出計算結果
    // 這裡使用原程式碼的轉型寫法 (emp1 as FullTimeEmployee).name 獲取屬性
    println("${(emp1 as FullTimeEmployee).name} 的年薪為: ${String.format("%.1f", emp1.calculateSalary())}")
    println("${(emp2 as PartTimeEmployee).name} 的月薪為: ${String.format("%.1f", emp2.calculateSalary())}")
}