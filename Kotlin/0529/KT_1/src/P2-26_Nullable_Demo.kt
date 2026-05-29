fun main() {

    var str: String? = null
    var len: Int
    var len1: Int?

    var str1: String? = "Mary"

    try {
    len = str!!.length   // 使用 !! 會強制轉換為非空類型，如果 str 為 null，則會拋出 NullPointerException
    }catch (e: Exception){
        println(e)      // 捕獲 NullPointerException 並打印異常信息

    }

    len = str1!!.length
    println("str1 的長度是 $len")

    if(str != null){
        len = str.length
    }

    len=str?.length?:0  // 使用 Elvis 運算符，如果 str 為 null，則 len 的值為 0
    println("str 的長度是 $len")


}
