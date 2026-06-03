//https://chatgpt.com/s/m_6a18f15650c48191a1ed755d844af980


fun main() {
    var name: String

    print("請輸入你的名字：")
    name = readln()
    try {
        if (name.isNullOrEmpty()) {
            throw Exception("名字不能為空！")
        }

        name.forEach {
            if (!it.isLetter()) {
                throw Exception("名字只能包含字母！")  // 如果名字中包含非字母字符，則拋出異常給 Catch message字段顯示
            }
        }
        println("你好，$name！")


    } catch (e: Exception) {       // 捕獲異常並處理
        println("catch event")
        println("錯誤：${e.message}")  //  顯示異常的消息內容
    }
}