fun main(contentToString: String.() -> Unit) {
    var list1 = listOf<Int>()
    var list2 = listOf<Int>(50)
    var list3 = listOf(11, 12, 13, 14, 15)
    var list4 = listOf("Mary", "John", "Nacy")
    var list5 = listOf<Any>("Mary", 50, 162.3f)

    println(list1)
    println(list2)
    println(list3)
    println(list4)
    println(list5)

    println(list3.size)
    println(list4.count())

    println(list3[1])
    println(list3.get(3))

    println()
    for (i in list4.indices)
        println(list4[i])

    println()
    for (data in list4)
        println(data)

    println()
    list5.forEach { println(it) }
    //list only set ,not had get

    //P7-4

    println(list3.sum())
    println(list3.average())
    println(list3.maxOrNull())
    println(list3.minOrNull())
    println(list3.contains(14))
    // containsAll() 方法：檢查 list3 是否包含所有指定元素
    // 參數：listOf(11,15,13) 是一個包含三個元素的 List
    // 回傳值：Boolean
    //   - true：list3 包含 11、15、13 三個元素（順序不重要）
    //   - false：list3 缺少任何一個元素
    //
    // 說明：
    // list3 = [11, 12, 13, 14, 15]
    // 檢查是否包含 [11, 15, 13] -> true（因為三個元素都在 list3 中）
    println(list3.containsAll(listOf(11, 15, 13)))
    println(list3.indexOf(13))

    list3 = listOf(11, 12, 11, 14, 15)
    var arr1 = list3.toString()
    println(arr1.contentToString())


}