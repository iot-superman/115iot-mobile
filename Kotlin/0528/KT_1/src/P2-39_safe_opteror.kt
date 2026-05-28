fun main() {

    //as ? 安全轉型運算子，當轉型失敗時會回傳null，而不是拋出異常
    var str: String = "12"
    var a: String? = str as? String
    var b:Int? = str as? Int
    var c:Int? = str.toInt() as? Int

}