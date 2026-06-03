fun main() {

    { print("Good Monring") } //Lambda expression

    run { print("Good Monring") } //run才會執行

    //Lambda expression with return type
    val greeting: () -> String = {
        val year = 2025
        "* Good Morning, Welcome to $year"
    }
    val data1: String = greeting()
    println(data1)


    //民國年轉西元年                  // yeaer:Int, 此Int也可不寫，會自動推斷
    val greetingYear: (Int) -> Int = { year: Int ->
        year + 1911
    }

    val year = greetingYear(115)
    println("year=$year")

    val greetingName: (String) -> String = { name ->
        "Hi $name ,welcome to SimVillage"   //最後一行就是回傳值，不需要寫return
    }
    println(greetingName("Mary"))

    // lambda with more than one parameter
    val greetingTwo = { name: String, numBuilding: Int ->

        println("Add $numBuilding houses")

        "Hi $name, welcome to new House"
    }

    println(greetingTwo("Mary", 5))

    // Lambda with no parameter and no return value
    val greetingSimple: () -> Unit = {
        println("Simple greeing")
        println("Hello world")
    }
    greetingSimple()

    val mathResult: (Int, Int, String) -> Int = { x, y, operator ->
        /*
        val result = when(operator){
            "+" -> x + y
            "-" -> x - y
            "*" -> x * y
            "/" -> x / y
            else -> 0
        }
        result     //最後一行就是回傳值，不需要寫return
        */
        //另一種寫法:
        // 直接回傳when的結果
        when (operator) {
            "+" -> x + y
            "-" -> x - y
            "*" -> x * y
            "/" -> x / y
//            "%" -> x % y
            else -> -1
        }
    }


    println("100+200=${mathResult(100, 200, "+")}")
    println("100-200=${mathResult(100, 200, "-")}")
    println("100*200=${mathResult(100, 200, "*")}")
    println("200/2=${mathResult(200, 2, "/")}")
    println("100%3=${mathResult(100, 3, "%")}") //因為%沒有定義，所以回傳-1






}


