
fun main(){
    var str: String = "123456789"

    var ch1:Char = str.first()
    var ch2: Char = str.last()

    println(ch1)
    println(ch2)
    //substring(startIndex: Int, endIndex: Int)方法返回一个新的字符串，包含从startIndex到endIndex-1的字符
    var subStr = str.substring(2,4 )
    println(subStr)

    //substring(range: IntRange)方法返回一个新的字符串，包含从range.first到range.last的字符
    subStr =str.substring(2..4)
    println(subStr)

}