fun main(){
    /*
    Kotlin var type range:
    Byte: 1 byte = 8 bits, range: -128 to 127
    Short: 2 bytes = 16 bits, range: -32,768 to 32,767
    Int: 4 bytes = 32 bits, range: -2,147,483,648 to 2,147,483,647
    Long: 8 bytes = 64 bits, range: -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807
    Float: 4 Bytes = 32 bits, range: 1.4E-45 to 3.4028235E38, precision: 6-7 digits
    Double: 8 Bytes = 64 bits, range: 4.9E-324 to 1.7976931348623157E308, precision: 15-16 digits
     */

    println("Byte.MIN_VALUE: ${Byte.MIN_VALUE}")  // -127
    println("Byte.MAX_VALUE: ${Byte.MAX_VALUE}")  // 127
    println("Short.MIN_VALUE: ${Short.MIN_VALUE}")  // -32768
    println("Short.MAX_VALUE: ${Short.MAX_VALUE}")  // 32767
    println("Int.MIN_VALUE: ${Int.MIN_VALUE}")  // -2147483648
    println("Int.MAX_VALUE: ${Int.MAX_VALUE}")  // 2147483647
    println("Long.MIN_VALUE: ${Long.MIN_VALUE}")  // -922337203685  4775808
    println("Long.MAX_VALUE: ${Long.MAX_VALUE}")  // 922337203685 4775807
    println("Float.MIN_VALUE: ${Float.MIN_VALUE}")  //          1.4E-45
    println("Float.MAX_VALUE: ${Float.MAX_VALUE}")  //          3.4028235E38
    println("Double.MIN_VALUE: ${Double.MIN_VALUE}")  //         4.9E-324
    println("Double.MAX_VALUE: ${Double.MAX_VALUE}")  //         1.7976931348623157E308



    //var num1: Byte 512   // Byte 127 ~ -128

    var num1: Byte = 127
    var num2: Int = 1000
    var num3: Short =20
    var num4: Float = 12.3f
    var num5 = 12.3
    var num6 = 512L
    var num7: Double = 1.1e2
//    var num8: Double = 2 //Error
    var num8: Double = 2.0






}