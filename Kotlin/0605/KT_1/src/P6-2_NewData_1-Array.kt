fun main() {
    var age: IntArray = intArrayOf(22, 23, 25, 26)
    var data = age[1]
    println("data  index 1=$data")

    data = age[3]
    println("data  index 3=$data")

    for (i in 0..age.size - 1)
        println("age index $i = ${age[i]}")

    println()
    for (data in age)
        println("data = $data")

    val score = floatArrayOf(100.5f, 125.3f, 55.7f)
    for (i in 0..score.size - 1)
        println("score index $i = ${score.get(i)}")

    println()
    val pass: BooleanArray = booleanArrayOf(true, false, true, true)
    for (i in 0..pass.size - 1) {
        if (pass[i])
            println("pass index $i = true")
        else
            println("pass index $i = false")
    }

    var arr3 = IntArray(5) { it } // 0,1,2,3,4
    for (data in arr3)
        println(data)


    var arr4 = IntArray(5) { 10 } // 10,10,10,10,10
    for (data in arr4)
        println(data)

    println()
    val arr6 = arr3 + arr4
    for (data in arr6)
        println(" $data ,")

    println()
    //P-5
    var arr0: Array<Int>
    arr0 = arrayOf(10, 11, 12, 13)
    for (data in arr0)
        println("arr1 data = $data")

    val plain: Array<String> = arrayOf("Jupiter", "Venus", "Saturn")
    for (name in plain)
        println("plain data = $name")


    println()

    //
    var arr7 = Array<Double>(5) { -1.0 } // -1.0, -1.0, -1.0, -1.0, -1.0
    for (value in arr7)
        println(value)


    //另一種寫法
    var arr8 = Array<String>(3, { it.toString() }) // 0,1,2
    for (data in arr8)
        println(data)


    println()

    arr8 = Array(5, { "$it value" })
    for (data in arr8)
        println(data)

    var arr1 = arrayOfNulls<Any>(5)
    arr1[0] = null
    arr1[1] = 1

    println()
    var arr = arrayOfNulls<Any>(3)
    arr[0] = "John"
    arr[1] = 18
    arr[2] = 55.12f

    println()


    var stu = arrayOf<Any>("John", 18, 55.12f)
    for (data in stu)
        println(data)

    println()
    var xArray: IntArray = intArrayOf(34, 12, 5, 67)
    var v = xArray.get(2)
    xArray.set(3, v)
    for (x in xArray)
        println(x)
    println()

      println()
    try {
        xArray.set(4,10)
    }catch (e: Exception){
        println("Error: $e")
    println()
    for (i in xArray.indices)  //size -1 = indices
        println("value =${xArray[i]}")

    println()
        for (i in 0 until xArray.size)  // until 可以避免越界
            println("value =${xArray[i]}")

    println()
    var sum=0
    xArray.forEach {
        sum+= it
        println("Sum: $sum")
    }
        var v_value : Iterable<IndexedValue<Int>> = xArray.withIndex()

        v_value?.forEach {
            println("index = ${it.index} value = ${it.value}")
        }

}







}



