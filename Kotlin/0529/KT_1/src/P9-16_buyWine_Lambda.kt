import java.util.concurrent.locks.Condition

fun main() {

    var str=""
    var age=0
    print("輸入年齡: ")
    age= readLine()?.toIntOrNull()?:0
    str=buyWine(age){ it >= 18 }  //Lambda 的參數 it 代表 buyWine 函式的 condition 參數，這裡的 Lambda 的回
    println(str)
    str=buyWine(age){ it >= 20 }  //Lambda 的參數 it 代表 buyWine 函式的 condition 參數，這裡的 Lambda 的回
    println(str)
}


fun buyWine(age: Int, condition: (Int)-> Boolean): String {
    val result: String

    if(condition(age)) {   //Lamdba func
        result = "可以買酒了"
    } else {
        result = "還不能買酒"
    }
    return result
}