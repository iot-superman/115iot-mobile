//https://chatgpt.com/s/m_6a27807656a081918df3632decdea778

//https://chatgpt.com/s/m_6a2781ea48a081918dcea54cd1e7d211

/**
 * 範例：示範 Kotlin 中不可變 Set 的建立與基本屬性。
 *
 * 說明重點：
 * - 使用 setOf() 建立 Set（重複元素只會保留一份）
 * - Set 的型別可以明確指定或由編譯器推斷
 * - 使用 size 或 count() 取得元素數量
 *
 * 檔案：`P7-11_SET.kt`
 */
fun main() {

    // 建立一個 Int 型別的 Set，包含重複的 44（重複項在 Set 中只會保留一個）
    var st1 = setOf<Int>(11, 22, 33, 44, 44,55)

    // 印出 st1
    println(st1)

    // 建立一個 String 型別的 Set
    var st2 = setOf<String>("AA", "AA", "BB", "BB")

    // 印出 st2
    println(st2)

    // 建立空的 Double Set
    var st3: Set<Double> = setOf<Double>()

    // 建立 Any 型別的 Set（可混合不同型別）
    var st4 = setOf("Mary", 20, 52.3)

    println(st3)
    println(st4)

    println()

    // 取得元素數量
    println(st2.size)

    // count() 也可取得元素數量
    println(st4.count())

    // SET 中的資料叫元素（element）

    println()

    // 使用 elementAt() 依索引取得元素
    println(st1.elementAt(2))

    // 超出範圍時回傳 null
    println(st1.elementAtOrNull(10))

    // 若為 null 則使用 Elvis operator 給預設值
    println(st1.elementAtOrNull(10) ?: "Not Found")

    // 使用 elementAtOrElse()
    // 若索引不存在則回傳 lambda 指定的值
    println(st1.elementAtOrElse(10) { -1 })

    // 回傳字串版本
    println(st1.elementAtOrElse(10) { "Not found" })

    println()
    st2.forEach { println(it) }
    println()
    st2.forEach { println(it) }
    println()
    st1.forEachIndexed { index, data -> println("$index : $data") }

    println()
    println(st1)
    println(st1.find(){it>30})

    println(st1.findLast{it>30}) //最後一個是Lamba 可以縮寫
    println(st1.filter { println(it);it>30})


    println()
    var st5 = setOf(st1.filter{it>30},listOf(1,2,3))
    println(st5)
    println(st5.joinToString ())

    println()
    for (data in st5)
        println(data)
    println()

    for(data in st5){
        for (item in data){
            print("$item, ")
        }
        println()
    }

//P 7-13 ====
    var mst1= mutableSetOf<Int>(1,1,2,2,3,4,5)
    println(mst1)
    var mst2 = mutableSetOf(11,22,33,44)
    println(mst2)

    println()
    mst2.add(5)
    println(mst2)
    mst2.addAll(setOf(55,66))
    println(mst2)

    mst2.remove(11)
    println(mst2)
    mst2.removeAll (setOf(33,55))
    println(mst2)

    //P7-14

    println()
    var mst3 = mst1.plus(6)
    println(mst3)
    mst3 = mst1.minus(2)
    println(mst3)

    println()
    println(mst1)
    var mst4= mst1+7
    println(mst4)
    mst4-=4
    println(mst4)

    //P7-14  轉換為陣列與串列

    println()
    var arr= mst1.toIntArray()
    println(arr)                     //print I@地址
    var lst  = mst1.toMutableList()
    println(lst)                     ////print 內容
    lst[0]=10
    lst.add(20)
    println(lst)

    println()
    var number =mutableListOf<Int>(1,1,2,2,3,4,5,5)
    println(number)
    var newSet = number.toSet()     //to 重複
    println(newSet)
    // 資籿
    number = newSet.toMutableList()
    println("number =$number")

    var nameList = mutableListOf<String>("AA","BB","CC","CC")
    println(nameList)
    var newName = nameList.toMutableSet()
    println(newName)
    newName.add("DD")
    newName.add("AA")
    println(newName)
    nameList = newName.toMutableList()
    println(nameList)
    nameList[0]="AA-1"
    println(nameList)

   //P7-15 , line 21-31
    var mst5 = setOf("Mary","John","Nacy","Brown")
    var mst6 = setOf("Leo","Mary","Brown","Joanna","Black")

    println("union")
    var mst7=mst5.union(mst6).toMutableSet()
    println("ms7="+mst7.joinToString())

    println("intersect")
    var mst8=mst5.intersect(mst6)
    println("mst8="+mst8.joinToString())

    println("subbract")
    var mst9 = mst5.subtract(mst6)
    println("mst9="+mst9.joinToString())
}