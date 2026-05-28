fun main() {
    var c1: Char = 'A'
    var c2: Char = '$'
    var c3: Char = '我'
//    var c4: Char = 'AB'  //字符类型只能存储一个字符，'AB'包含两个字符，所以会导致编译错误

    println(c1)
    println(c2)
    println(c3)


//    var c4:int=c1+1 //error ,
    var c5:Char = c1+1  //c5='B'
    println(c5)

}