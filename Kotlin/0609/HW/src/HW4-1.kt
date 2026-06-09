fun main() {
    // 已知資料：二維陣列
    val revenue = arrayOf(
                     //   1  ...  12月
        intArrayOf(120, 135, 150, 140, 160, 180, 190, 210, 200, 195, 220, 250), // A店
        intArrayOf(100, 110, 130, 145, 155, 170, 185, 195, 205, 215, 225, 240), // B店
        intArrayOf(90, 105, 120, 130, 145, 160, 175, 185, 190, 200, 210, 230),  // C店
        intArrayOf(110, 125, 140, 150, 165, 175, 180, 205, 210, 220, 235, 260)  // D店
    )

    // 呼叫 4 個不同的功能函式
    F1_analyzeShopRevenue(revenue)
    F2_findMaxRevenueShop(revenue)
    F3_analyzeMonthlyRevenue(revenue)
    F4_findMaxRevenueMonth(revenue)
}

// ==========================================
// 功能一：計算每間分店 12 個月的總營收及平均月營收
// ==========================================
fun F1_analyzeShopRevenue(revenue: Array<IntArray>) {
    println("一：計算每間分店 12 個月的總營收及計算每間分店平均月營收")
    val shopNames = arrayOf("A店", "B店", "C店", "D店")

    for (i in revenue.indices) {    //每家分店 index :0.. size-1
        var total = 0
        for (j in revenue[i].indices) {
            total += revenue[i][j]
        }
        val average = total.toDouble() / 12    //平均
        println("${shopNames[i]}全年營收： ${total} 萬元 , 平均月營收： ${String.format("%.2f", average)} 萬元")
    }
    println("---")
}

// ==========================================
// 功能二：比較四間分店的全年總營收,找出全年營收最高的分店
// ==========================================
fun F2_findMaxRevenueShop(revenue: Array<IntArray>) {
    println("二：比較四間分店的全年總營收,找出全年營收最高的分店")
    val shopNames = arrayOf("A店", "B店", "C店", "D店")

    var maxShopIndex = 0
    var maxShopRevenue = -1

    for (i in revenue.indices) {
        var total = 0
        for (j in revenue[i].indices) {
            total += revenue[i][j]
        }
        // 第一間店直接設定或發現更高營收時更新最大值和店索引
        if (i == 0 || total > maxShopRevenue) {
            maxShopRevenue = total
            maxShopIndex = i
        }
    }
    println("全年營收最高分店： ${shopNames[maxShopIndex]}")
    println("全年營收： ${maxShopRevenue} 萬元")
    println("---")
}

// ==========================================
// 功能三：統計所有分店在每個月份的總營收
// ==========================================
fun F3_analyzeMonthlyRevenue(revenue: Array<IntArray>) {
    println("三：統計所有分店在每個月份的總營收")
    // 固定 12 個月
    for (month in 0 until 12) {
        var monthlySum = 0
        for (shop in revenue.indices) {
            monthlySum += revenue[shop][month]
        }
        println("${month + 1}月總營收： ${monthlySum} 萬元")
    }
    println("---")
}

// ==========================================
// 功能四：比較 12 個月份的總營收 , 找出全公司營收最高的月份
// ==========================================
fun F4_findMaxRevenueMonth(revenue: Array<IntArray>) {
    println("四：比較 12 個月份的總營收 , 找出全公司營收最高的月份")
    var maxMonthIndex = 0
    var maxMonthRevenue = -1

    for (month in 0 until 12) {
        var monthlySum = 0
        for (shop in revenue.indices) {
            monthlySum += revenue[shop][month]
        }
        // 第一個月或發現更高營收時更新
        if (month == 0 || monthlySum > maxMonthRevenue) {
            maxMonthRevenue = monthlySum
            maxMonthIndex = month
        }
    }
    println("全公司營收最高月份： ${maxMonthIndex + 1} 月")
    println("總營收： ${maxMonthRevenue} 萬元")
}