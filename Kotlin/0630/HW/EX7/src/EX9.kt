// 1. 定義 PaymentMethod 介面
interface PaymentMethod {
    fun pay(amount: Int): Boolean
}

// 2. 定義 CreditCard 類別，實作 PaymentMethod 介面
class CreditCard : PaymentMethod {
    init {
        println("建立 CreditCard 物件。")
    }

    override fun pay(amount: Int): Boolean {
//        println("使用信用卡付款：$amount 元 ")
        return true
    }
}

// 3. 定義 Cash 類別，實作 PaymentMethod 介面
class Cash : PaymentMethod {
    init {
        println("建立 Cash 物件。")
    }

    override fun pay(amount: Int): Boolean {
//        println("使用現金付款：$amount 元 ")
        return true
    }
}

// 4. 定義 Checkout 類別負責處理付款流程
class Checkout {
    init {
        println("建立 Checkout 物件。")
    }

    fun processPayment(amount: Int, method: PaymentMethod) {
        val result = method.pay(amount)
//        if (result) {
//            println("結帳系統提示：付款流程順利完成。\n")
//        } else {
//            println("結帳系統提示：付款失敗。\n")
//        }
    }
}

fun main() {
    // 使用 Kotlin 原生的 readln() 取代 Scanner
    print("請輸入信用卡付款金額CreditCard: ")
    val creditAmount = readln().toInt()

    print("請輸入現金付款金額Cash: ")
    val cashAmount = readln().toInt()

    println("\n執行流程：")

    // 1. 建立 Checkout 物件
    val checkout = Checkout()

    // 2. 建立 CreditCard 物件
    val creditCard = CreditCard()

    // 3. 建立 Cash 物件
    val cash = Cash()

    // 4. 使用 CreditCard 支付
    println("使用 CreditCard 支付 $creditAmount 元。")
    checkout.processPayment(creditAmount, creditCard)

    // 5. 使用 Cash 支付
    println("使用 Cash 支付 $cashAmount 元。")
    checkout.processPayment(cashAmount, cash)


}