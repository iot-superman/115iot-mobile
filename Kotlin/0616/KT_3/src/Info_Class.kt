import java.util.regex.Pattern
//https://chatgpt.com/s/m_6a30b9992bf481919f0222efa19ace13
class Info(name: String, age: Int) {

    var name: String
    var age = 0

    var email = "No Email"

    var phone: String = "No Phone"

    init {

        // 設定姓名
        this.name = name

        // 設定年齡
        this.age = age
    }

    //==============================
    // 第1個次要建構子
    //==============================
    constructor(
        name: String,
        age: Int,
        email: String = ""
    ) : this(name, age) {

        // Email 必須包含 @

        if (email.contains("@")) {
            this.email = email
        }
    }

    //==============================
    // 第2個次要建構子
    //==============================
    constructor(
        name: String,
        age: Int,
        email: String = "",
        phone: String = ""
    ) : this(name, age, email) {

        // 電話格式：
        // 4碼-4碼
        // 例如：2274-5678

        val regex = Pattern.compile("^\\d{4}-\\d{4}$")

        if (regex.matcher(phone).matches()) {
            this.phone = phone
        }
    }
    fun dataPrint(){
        println("Name: ${this.name}, age: ${this.age}, Email: ${this.email}, phone: ${this.phone}")
    }
}



