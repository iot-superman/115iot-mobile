import kotlin.math.hypot

fun main() {

    // =====================================================
    // 陣列比較
    // =====================================================

    var arr3 = intArrayOf(2, 3, 4, 5)
    var arr4 = intArrayOf(2, 3, 4, 5)

    // arr5 指向 arr3 同一個記憶體位置
    var arr5 = arr3

    // =====================================================
    // == 比較的是記憶體位置（參考位置）
    // =====================================================

    // false
    // arr3 與 arr4 是不同物件
    println(arr3 == arr4)

    // true
    // arr5 與 arr3 指向同一個物件
    println(arr3 == arr5)

    // =====================================================
    // contentEquals()
    // 比較陣列內容
    // =====================================================

    // true
    // 因為元素內容相同
    println(arr3.contentEquals(arr4))

    println()

    // =====================================================
    // clone() 與 copyOf()
    // =====================================================

    /**
     * 說明：
     * - clone()：完整複製陣列
     * - copyOf(n)：
     *      n 小於原長度 → 截斷
     *      n 大於原長度 → 補預設值 0
     */

    var arr = intArrayOf(34, 12, 5, 67, 17, 44)

    // clone()
    var arrCpy1 = arr.clone()

    // copyOf(3)
    var arrCpy2 = arr.copyOf(3)

    // copyOf(10)
    var arrCpy3 = arr.copyOf(10)

    println(arrCpy1.contentToString())
    println()

    println(arrCpy2.contentToString())
    println()

    println(arrCpy3.contentToString())
    println()

    // false
    // 比較的是參考位置
    println(arr == arrCpy1)

    // true
    // 比較的是內容
    println(arrCpy1.contentEquals(arr))

    println()

    // =====================================================
    // all / any / contains / elementAtOrNull
    // =====================================================

    arr = intArrayOf(34, 12, 5, 67, 17, 7, 44)

    // all()
    // 是否全部都 > 20
    println(arr.all { it > 20 })

    println()

    // any()
    // 是否存在 <= 5
    println(arr.any { it <= 5 })

    println()

    // elementAtOrNull()
    // 超過索引不會錯誤
    println(arr.elementAtOrNull(10))

    println()

    // contains()
    println(arr.contains(5))

    println()

    // =====================================================
    // 排序
    // =====================================================

    var arr1: IntArray
    var arr2: IntArray

    // 降冪排序（建立新陣列）
    arr2 = arr.sortedArrayDescending()

    println(arr2.contentToString())

    println()

    // =====================================================
    // sort()
    // 直接修改原本陣列
    // =====================================================

    arr.sort()

    println(arr.contentToString())

    arr.sort()

    println(arr.contentToString())

    println()

    // =====================================================
    // sorted()
    // 回傳 List<Int>
    // 不修改原本陣列
    // =====================================================

    var newArr = arr.sorted()

    println(newArr)

    println()

    // =====================================================
    // 重新指定 arr
    // =====================================================

    arr = intArrayOf(34, 12, 5, 67, 17, 12, 7, 44)

    // 修正：
    // 避免 newArr 重複宣告
    var newArr2: List<Int> = arr.sorted()

    println(newArr2)

    // 原陣列不變
    println(arr.contentToString())

    println()

    // =====================================================
    // sortedArray()
    // 回傳新的 IntArray
    // =====================================================

    var newArr1: IntArray = arr.sortedArray()

    println(newArr1.contentToString())

    println()

    // =====================================================
    // average()
    // 平均值
    // =====================================================

    var result: Double = arr.average()

    println(result)

    println()

    // =====================================================
    // P6-29
    // 二維陣列建立
    // =====================================================

    // 建立 3x4 二維陣列
    // 初始值全部為 0

    var xArr = Array(4) { Array(4) { 0 } } //4 x 4

    println("===== 初始二維陣列 =====")

    for (data in xArr) {

        println(data.contentToString())
    }

    println()

    // =====================================================
    // P6-30
    // 二維陣列填值
    // =====================================================

    var number = 1

    // 雙層迴圈填值

    for (i in xArr.indices) {

        for (j in xArr[i].indices) {

            xArr[i][j] = number

            number++
        }
    }

    // =====================================================
    // 印出填值後結果
    // =====================================================

    println("===== 填值後 =====")

    for (data in xArr) {

        println(data.contentToString())
    }

    println()

    // =====================================================
    // 使用雙層迴圈輸出
    // =====================================================

    println("===== 雙層迴圈輸出 =====")

    for (i in xArr.indices) {

        for (j in xArr[i].indices) {

            print("${xArr[i][j]}\t")
        }

        println()
    }

    println()

    xArr = arrayOf(
        arrayOf(1, 2, 3, 4),
        arrayOf(5, 6, 7, 8),
        arrayOf(9, 10, 11, 12)
    )
    for (data in xArr) {
        println(data.contentToString())
    }
    // P6-31
    // P6-37 三維陣列建立
    println("三維陣列建立")


    // =====================================================
    // P6-31
    // P6-37 三維陣列建立
    // =====================================================

    println()

    // 三維陣列：
    // 第1層 -> 大組
    // 第2層 -> 小組
    // 第3層 -> 資料內容

    var yArr = arrayOf(

        // =========================
        // 第 1 大組
        // =========================
        arrayOf(
            arrayOf(1, 2, 3, 4),
            arrayOf(5, 6, 7, 8),
            arrayOf(9, 10, 11, 12)
        ),

        // =========================
        // 第 2 大組
        // =========================
        arrayOf(
            arrayOf(13, 14, 15, 16),
            arrayOf(17, 18, 19, 20),
            arrayOf(21, 22, 23, 24)
        )
    )

    // =====================================================
    // 印出三維陣列內容
    // =====================================================

    for (i in yArr.indices) {

        println("第 ${i + 1} 層")

        for (j in yArr[i].indices) {

            for (k in yArr[i][j].indices) {

                print("${yArr[i][j][k]}\t")
            }

            println()
        }

        println()
    }

    for (level in yArr)
        for (row in level) {
            println(row.contentToString())
        }
    println()

}
