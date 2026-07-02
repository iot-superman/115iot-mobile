/*
題目 1：
請設計一個 Student的 data類別，包含以下屬性:
屬性：姓名（name）、年齡（age）、成績（score）

再設計一個 ScoreManager 類別，內含：

三個函式：
  * addStudent(student:Student):
  * listPass()：印出成績大於等於 60 學生資訊
  * printInfo()：印出學生資訊，例如 `"姓名：Tom，年齡：18，成績：85

請在主程式中建立 4 個學生資料，

姓名	年齡	 成績
Tom	 18	 85
Amy	 17	 92
Bob	 19	 55
Jane 18	 60

主程式需完成以下步驟：

建立 4 個 Student 物件。
使用 addStudent() 將 4 位學生加入管理系統。
使用 printInfo() 列印所有學生資訊。
使用 listPass() 列印及格學生資訊。

 */

data class Student(val name: String, val age: Int, val score: Int)
class ScoreManager {
    private val students = mutableListOf<Student>()

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun listPass() {
        println("及格學生資訊：")
        for (student in students) {
            if (student.score >= 60) {
                println("姓名：${student.name}，年齡：${student.age}，成績：${student.score}")
            }
        }
    }

    fun printInfo() {
        println("所有學生資訊：")
        for (student in students) {
            println("姓名：${student.name}，年齡：${student.age}，成績：${student.score}")
        }
    }
}

fun main() {
    val scoreManager = ScoreManager()

    val student1 = Student("Tom", 18, 85)
    val student2 = Student("Amy", 17, 92)
    val student3 = Student("Bob", 19, 55)
    val student4 = Student("Jane", 18, 60)

    scoreManager.addStudent(student1)
    scoreManager.addStudent(student2)
    scoreManager.addStudent(student3)
    scoreManager.addStudent(student4)

    scoreManager.printInfo()
    scoreManager.listPass()
}