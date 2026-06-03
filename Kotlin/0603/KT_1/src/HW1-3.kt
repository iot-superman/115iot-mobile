/*
### 🔹 題目 1：計算數字平方值

使用匿名函數，傳入一個整數，計算這整數的平方，回傳結果。呼叫這個函數並印出結果。


// 請完成：使用匿名函數計算 7 和 15 的平方

```

## 🔹 題目 2：判斷奇偶數

撰寫匿名函數，傳入一個整數，回傳 "Odd" 或 "Even"，然後呼叫該函數並印出結果。


// 範例輸入：5
// 輸出："Odd"

/ 範例輸入：16
// 輸出："Even"
```

### 🔹 題目 3：計算利息 和本利和

定義一個 匿名函數，輸入：本金 和 年利率 和年限 ,列印每位客戶的本金、利息和本利和
* 本金（principal: Int）
* 年利率（interest :Float ）
* 年限（year: Int)

// 範例輸入：50000 , 1.75% , 10
// 輸出：本金$50000 存放10年的利息$xx 和本利和$xx

// 範例輸入：90000 , 3.5% , 15
// 輸出：本金$90000 存放15年的利息$xx 和本利和$xx

 */
fun main() {

    // EX3.1: 從鍵盤讀入一個整數，使用 lambda 計算平方並印出
    // 定義一個 lambda：接收 Int，回傳其平方
    val square: (Int) -> Int = { it * it }
    println("EX3.1")
    val x = 7
    val y = 15

    // 呼叫並印出結果
    println("square($x) = ${square(x)}")   // 輸出：square(7) = 49
    println("square($y) = ${square(y)}")   // 輸出：square(15) = 225
    println()


    // =========================
    // EX3.2：奇偶判斷（使用 lambda，從鍵盤讀取）
    // =========================
    println("EX3.2 - 判斷奇偶（使用 lambda）")
    val isOdd: (Int) -> String = { if (it % 2 == 0) "Even" else "Odd" }
    print("請輸入一個整數來判斷奇偶: ")
    val num = readln().toIntOrNull() ?: 0  // 安全轉換，預設為 0
    println("輸入的數字 $num 是 ${isOdd(num)}")

    println()

    println("EX3.3")

    // =====================================================
    // EX3.3：計算利息與本利和
    // =====================================================

    println("EX3 - 計算利息與本利和")

    // =========================
    // 鍵盤輸入本金
    // =========================
    println("請輸入本金：")
    val principal = readln().toInt()

    // =========================
    // 鍵盤輸入年利率
    // =========================
    println("請輸入年利率(%): ")
    val interest = readln().toFloat()

    // =========================
    // 鍵盤輸入年限
    // =========================
    println("請輸入年限：")
    val year = readln().toInt()



// =====================================================
// Lambda：計算單利、複利與本利和
// =====================================================

    val calculateInterest: (Int, Float, Int) -> Unit =
        { p, i, y ->

            // 百分比轉小數
            val rate = i / 100


            // =========================
            // 單利
            // =========================
            val simpleInterest = p * rate * y

            // 單利本利和
            val simpleTotal = p + simpleInterest


            // =========================
            // 複利
            // =========================
            val compoundTotal =
                p * Math.pow((1 + rate).toDouble(), y.toDouble())

            val compoundInterest = compoundTotal - p


            // =========================
            // 輸出結果
            // =========================

            println()


            println("【複利計算】")

            println(
                "本金$$p 存放${y}年的利息$${"%.2f".format(compoundInterest)} " +
                        "和本利和$${"%.2f".format(compoundTotal)}"
            )
        }

// 呼叫 Lambda
    calculateInterest(principal, interest, year)
}

