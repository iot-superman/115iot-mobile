fun main() {
    var str4 ="""
        |"The frist line string\n"
            |the sencond line string\n
                the third line string.
            
    """
//trimIndent()會將每行的前置空白去掉，直到最少的前置空白為止。
    println(str4)
    println()
    println(str4.trimIndent())

    var str5 ="""
        |"The frist line string5\n"
            |the sencond line string5\n
                the third line string.
            
    """
    println()
    //trimMargin 會將每行的前置空白去掉，直到最少的前置空白為止，並且會將指定的邊界符號（默認為|）去掉。
    println(str5.trimMargin())

    var str6 ="""
        @"The frist line string6\n"
            @the sencond line string6\n
                the third line string.
            
    """
    println()
    //trimMargin 會將每行的前置空白去掉，直到最少的前置空白為止，並且會將指定的邊界符號（@）去掉。
    println(str6.trimMargin("@"))
}