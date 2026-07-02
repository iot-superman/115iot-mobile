object randNumber {
    var num: Int = 1
        set(value) {
            // Fixes the incomplete if-else statement
            field = if (value < 1) 1 else value
        }

    var id: Int

    init {
        id = 100
    }

    // Renamed from getRandom to genNumbers to match your main() function call
    fun genNumbers(): IntArray {
        // Generates an array of random integers based on the size of 'num'
        return IntArray(num).apply {
            for (i in indices) {
                this[i] = (1..100).random()
            }
        }
    }
}

object Logger {
    var count: Int = 0

    init {
        count = 0
        println("count = $count, Welcome to Logger")
    }

    fun log(message: String) {
        println("Log: $message")
        count++
    }

    fun logCount(): Int = count
}

interface LogMag {
    fun log(message: String) {
        println("Log : $message")
    }
}

class  AppLogger:LogMag
{
    fun myLog(myClick: Click){
        myClick.click()
        log("AppLogger myLog")
    }
}
open class  Car(val brand: String){
    open fun basicInfo(name: String): String{
        println("Car brand = $brand, name = $name")
        return "sale name is $name"
    }
}

fun main() {

    var myCar =Car("BMW")
    println(myCar.basicInfo("John"))

    myCar = object : Car("Toyota") {
        override fun basicInfo(name: String): String {
            println("This Car brand is $brand")
            return "sale name is $name"
        }
    }



    var button = object: Click{
        override fun click() {
            println("Button clicked")
        }
    }
    button.click()
    println()
    val appLog =AppLogger()
    appLog.myLog(object : Click{
        override fun click() {
            println("my Log is clicked")
        }
    })


    var button1= object  : Button() {
        override fun draw(){
            super.draw()
            println("button 1 object drawing")
        }

        override fun click() {
            super.click()
            println("button1  object click")
        }

    }


    // 1. Using randNumber to generate random numbers
    randNumber.num = 5
    var numbers = randNumber.genNumbers()
    numbers.forEach {
        print("$it ,")
    }

    println()
    // 2. Interacting with the Logger singleton object
    println("count = ${Logger.logCount()} ")

    println()
    Logger.log("Log test 1")
    println("count = ${Logger.logCount()} ")

    println()
    Logger.log("Log test 2")
    println("count = ${Logger.logCount()} ")
}