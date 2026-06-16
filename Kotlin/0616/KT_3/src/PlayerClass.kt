import java.util.Locale

class PlayerClass {

    var name = ""
    var score = 0

    fun show() {
        println("$name , score: $score")
    }

    fun showNew() {
        println("$name , score: $score")
    }

    fun setData(
        newName: String,
        newScore: Int = score
    ) {
        name = newName
        score = newScore
    }
}

class ToUpper {

    var str: String = ""
        set(value) {
            if (value.isEmpty())
                field = "No data"
            else
                field = value.uppercase(Locale.getDefault())
        }

        get() {
            return "transfer data : $field"
        }
}

class Player {

    // 老師的是 private
    private var phone = "123456"

    var name = "Mary"
        get() = field.uppercase()
        set(value) {
            field = value.trim()
        }

    var id: Int = 1
        get() = field + 100
        set(value) {

            if (value > 10) {
                println("id is error: id 必須小於等於 10")
                field = 0
            } else {
                field = value
            }
        }

    var email = "aaa@gmail.com"
        get() = field.lowercase()
        set(value) {

            if ("@" in value) {
                field = value
            } else {
                println("Email 格式錯誤")
            }
        }

    var code: Int
        get() = id - 100
        set(value) {
            id = value
        }

    fun attrPrint() {
        println(
            "name = $name , id = $id , email = $email , phone=$phone"
        )
    }

    fun changePhone(newPhone:String) {
        phone = newPhone
    }


}


class NewPlayer1(_name: String,_age: Int, _isBlessed:Boolean){


    var name = _name
        get() = field.lowercase()
        set(value) {
            if (value.length>10){
                println("name is too long")
                field ="erro"
            }else{
                field = value
            }
        }
    var age  = _age
    var isBlessed =_isBlessed

    init {
        println("do init")
    }

    fun attrPrint() {
        println("name = $name , age = $age , isBlessed = $isBlessed")
    }
}
                                           //
class newPlayer2(var _name: String, var _age: Int, var healthPoint: Int = 80) {
    //var 在參數前面表示會自動建立一個屬性，並且將參數的值賦予該屬性,後面就不需要再宣告一次了(無需再宣告一次 var name = _name
    //如果沒有 var 就不會自動建立屬性，也無法在類別內部使用該參數的值或寫get和set方法

    fun attrPrint() {
        println("name = $_name , age = $_age , healthPoint = $healthPoint")
    }
}

class newPlayer3(_name: String, var age: Int=20, var healthPoint: Int = 90) {

    var name = _name
        get() = field.lowercase()


    fun attrPrint() {
        println("name = $name , age = $age , healthPoint = $healthPoint")
    }
}