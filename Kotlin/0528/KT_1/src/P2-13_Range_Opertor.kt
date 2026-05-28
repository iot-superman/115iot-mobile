fun main() {
    var r = 2..8
    for (i in r)
        print("i=$i ,")
    println()

    for (x in 2..8 step 2)
        print("x=$x ,")
    println()

    for (data in 'E'..'H')
        print("data=$data ,")
    println()

    //until 運算符用於創建一個不包含結束值的範圍
    for (i in 2 until 8)
        print("i=$i ,")
    println()

    //until, step 2
    for (x in 2 until 8 step 2)
        print("x=$x ,")
    println()

    //down to 運算符用於創建一個從較大值到較小值的範圍，前後值都包含
    for (i in 6 downTo 1)
        print("i=$i ,")
    println()

    //down to, step 2
    for (x in 6 downTo 1 step 2)
        print("x=$x ,")
    println()

    var result = 1 in 2..8
    println("result = $result")

    result = 3 in 2..8
    println("result = $result")

    result = 1 !in 2..8  //! not in
    println("result = $result")

    //scanf age
    print("請輸入年齡:")
    var newAge = readln().toInt()
    println("年齡 = $newAge")
    if (newAge in 0..5)
        println("It is preschool age. ")
    else if (newAge in 6..11)
        println("It is school children age.")
    else if (newAge in 12..18)
        println("It is teenager age. ")
    else if (newAge in 19..24)
        println("It is youth")
    else
        println("It is adult")

//    newAge = readln().toInt()

/**
  val 變量名 = if(條件) 值1 else if(條件2) 值2 else 值3
 */
val ageStatus = if(newAge in 0..5)
                    // 0 到 5 歲：學前期
                    "It is preschool age. "
                else if (newAge in 6..11)
                    // 6 到 11 歲：學童期
                    "school children age"
                else if (newAge in 12..18)
                    // 12 到 18 歲：青少年期
                    "teenager age"
                else if (newAge in 19..24)
                    // 19 到 24 歲：青年期
                    "youth"
                else
                    // 25 歲及以上：成人期
                    "adult"

    println("ageStatus = $ageStatus")

    println()

    print("Input fruit :")
    var fruit = readln().lowercase()
    var price = if (fruit == "apple")
                    100
                else
                    50
    println("price = $price")

    //String 比較
    val data1="abc"
    val data2="ABC"

    //Kothin String 比較可以使用 == 或 equals() 方法，兩者在比較內容時效果相同，
    // 都是比較字符串的值是否相等，而不是比較引用地址。
    println("""
    //Kothin String 比較可以使用 == 或 equals() 方法，兩者在比較內容時效果相同，
    // 都是比較字符串的值是否相等，而不是比較引用地址。   
    """.trimIndent())
    // == 比較字符串的值是否相等，大小寫敏感是不同的
    if(data1==data2)
        println("data1 is equal to data2")
    else
        println("data1 is not equal to data2")

    println()
    // equals() 方法比較字符串的值是否相等
    if (data1.equals(data2.lowercase())) // data1 == data2.lowercase() 也可以
        println("data1 is equal to data2")
    else
        println("data1 is not equal to data2")









}