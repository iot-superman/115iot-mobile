import java.io.File
import java.io.FileReader
import java.io.FileWriter

fun main() {
    // 1. 取得並印出目前專案的執行路徑（工作目錄）
    val dir = File("").absolutePath
    println("current path: $dir")

    // 2. 在 C 槽建立一個名為 "kotlin" 的新資料夾
    val newDir = File("c:\\kotlin")
    val result = newDir.mkdir() // 成功回傳 true，失敗或資料夾已存在回傳 false
    println("result = $result")

    // 3. 使用 Kotlin 的擴充函式操作 data1.txt
    val file = File("c:\\kotlin\\data1.txt").apply {
        createNewFile()                        // 建立新檔案
        writeText("Hello Mary \n")             // 寫入文字（會覆蓋舊內容）
        appendText("Good morning !\n")         // 附加文字
        appendText("This is kotlin class. \n") // 再次附加文字
    }

    println()
    // 4. 使用 Kotlin 內建的 readText() 一次性讀取 data1.txt 完整內容
    val data = file.readText()
    println(data)

    println()
    // 5. 使用 Kotlin 內建的 readLines() 按行讀取 data1.txt
    val lines = file.readLines()
    lines.forEach { line ->
        println("data = $line")
    }

    println()

    // ==================== 以下為新增的 FileWriter / FileReader 操作 ====================

    // 6. 使用 FileWriter 寫入新檔案 data2.txt
    // 第二個參數為 append 模式：false 代表「覆蓋寫入」，true 代表「附加寫入」
    val newFile = FileWriter("c:\\kotlin\\data2.txt", false)

    // .use 區塊是 Kotlin 的語法糖，相當於 Java 的 try-with-resources
    // 它能確保區塊執行完畢後，自動關閉（close）FileWriter 流，釋放檔案鎖定
    newFile.use {
        // write(int) 寫入的是該十六進位數值對應的 ASCII / Unicode 字元
        it.write(0x31)     // 0x31 在 ASCII 中代表字元 '1'
        it.write(0x38)     // 0x38 在 ASCII 中代表字元 '8'
        it.write(0x41)     // 0x41 在 ASCII 中代表大寫字元 'A'
        it.write("\n")     // 寫入換行符號

        // 寫入字元陣列 (CharArray)
        val data = charArrayOf('H', 'e', 'l', 'l', 'o', '\n')
        it.write(data)

        // 直接寫入字串
        it.write("It is sunny day\n")
    } // ← 執行到此處時，FileWriter 會被自動關閉並將緩衝區資料刷入硬碟，後續才能正確讀取

    // 7. 使用 Kotlin 的方法快速讀取 data2.txt 的內容並印出
    val readData = File("c:\\kotlin\\data2.txt").readText()
    println(readData)

    // 8. 使用 FileReader 配合字元陣列（緩衝區）來讀取檔案
    val chBuff = CharArray(50) // 建立一個長度為 50 的字元陣列作為讀取緩衝區
    var len = 0                // 用來記錄實際讀取到的字元數量
    var myFile = FileReader("c:\\kotlin\\data2.txt")

    myFile.use {
        // read(chBuff) 會把檔案內容讀入 chBuff 陣列中，並回傳實際讀取到的字元長度
        len = it.read(chBuff)
        println("len = $len")
    }

    // 9. 印出讀取到的字元陣列
    // 方法 A：利用 String 的建構子，將字元陣列從索引 0 開始、長度為 len 的部分轉為字串印出
    println(String(chBuff, 0, len))
    println()

    // 方法 B：使用 for 迴圈，逐一印出字元陣列中的每個字元
    for (i in 0 until len) {
        print(chBuff[i])
    }
    println()

    // 10. 重新開啟 FileReader 並利用 Kotlin 的擴充函式按行讀取
    myFile = FileReader("c:\\kotlin\\data2.txt")
    myFile.use {
        // FileReader 本身沒有 readLines()，這是 Kotlin 為 Reader 類別提供的便利擴充函式
        val dataList = it.readLines()
        for (data in dataList) {
            println(data)
        }
    }
}