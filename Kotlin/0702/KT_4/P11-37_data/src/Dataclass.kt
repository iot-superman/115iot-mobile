/**
 * 範例資料類別，用於示範 data class 的產生方法與比較行為
 *
 * 使用不同名稱 `MyDataModel` 以避免與專案中其他 `MyData` 類別命名衝突。
 *
 * @property name 姓名
 * @property height 身高（公尺）
 * @property weight 體重（公斤）
 */
data class MyDataModel(
    var name: String,
    var height: Float,
    var weight: Float
) {
    /** 年齡，預設為 0，可在建立後修改 */
    var age: Int = 0
}

data class Student(
    val id: Int,
    val name: String,
    val age:Int,
    val grade: String
)

object StudentManager {
    val students = mutableListOf<Student>()

    fun addStudent(student: Student) {
        students.add(student)
        println("add student ${student.name}")
    }

    fun showAllStudents() {
    println("List all student")
        students.forEach {
            println("ID:${it.id} Name: ${it.name} Age:${it.age} Grade:${it.grade}")
        }
    }

    fun removeStudentById(id: Int){
        val remove = students.removeIf { it.id == id }
        if (remove)
            println("Student removed: $id")
        else
           println("id is wrong")
    }

}
enum class Dicection{
   EAST,WEST,SOUTH,NORTH;  //東、西、北、南
}
fun move(dir:Dicection){
    when(dir){
        Dicection.EAST -> println("Go right")
        Dicection.WEST -> println("Go left")
        Dicection.SOUTH -> println("Go down")
        Dicection.NORTH -> println("Go up")
    }
}

enum class Day(val chinese: String){
    Monday(chinese="星期一"),
    Tuesday(chinese="星期二"),
    Wednesday(chinese="星期三"),
}

data class Data1(val name: String, val id: Int)
class MyPrint2:Process<Data1>{


    override fun process(item: Data1) {
        println("name: ${item.name}")
        println("id  : ${item.id}")
    }
}

fun main() {



    var today=Day.Monday
    println(today)
    println("index=${today.ordinal}")
    println("input: ${today.chinese}")

    var dir = Dicection.EAST
    println(dir)
    println(dir.name)


    println("index = ${dir.ordinal}")



    println("\n========")

    var data1 = MyDataModel(
        name = "Mary",
        height = 1.7f,
        weight = 54.4f
    )
    data1.age = 20
    println(data1)
    println(data1.toString())


println()
    var data = MyDataModel("Mary",1.7f, 54.4f)
    println(data==data1)
    println(data.equals(data1))
    println(data===data1)
    println()

    var data2 =data1
    println(data2 == data1)
    println(data2 === data1)
    data1.name ="Brown"
    println(data2.name)

    println()
//    StudentData.addStudent(Student(1, "Mary", 20, "A"))
//    StudentData.addStudent(Student(2, "John", 19, "B"))
//    StudentData.addStudent(Student(3, "David", 21, "A"))
//
//    StudentData.showAllStudents()
//
//    println()
//
//    StudentData.removeStudentById(2)
//
//    println()
//
//    StudentData.showAllStudents()
    val s1= Student (101,"Alice", 18, "A")
    val s2= Student (102,"Bob", 19, "B")
    val s3 = Student(103,"Charlie", 20, "C")
    val s4 = Student(104,"David", 21, "D")

    StudentManager.addStudent(s1)
    StudentManager.addStudent(s2)
    StudentManager.addStudent(s3)
    StudentManager.addStudent(s4)
    println()
    StudentManager.showAllStudents()
    println()

    StudentManager.removeStudentById(102)
    StudentManager.showAllStudents()
    println()
    println()






}