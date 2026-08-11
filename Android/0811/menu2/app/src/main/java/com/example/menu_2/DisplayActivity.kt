package com.example.menu_2

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_display)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "Display Page"
        // 顯示標題列的返回按鈕
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    // gemini ai version:
    /*
    這個函數 onSupportNavigateUp() 的作用與 onOptionsItemSelected 非常相似，但它更專注於**標題列左上角那個「返回箭頭」**的點擊事件。
我們可以從以下幾個重點來理解：
1. 它是什麼？
這是 AppCompatActivity 提供的一個專門鉤子（Hook），當使用者點擊了標題列左側的 Up Button（向上按鈕）時，系統會自動呼叫這個函數。
2. 裡面的程式碼在幹嘛？
•
onBackPressedDispatcher.onBackPressed()： 這行程式碼的意思是：「去執行使用者按下手機實體 『返回鍵』 時該做的動作」。
◦
在現代 Android 開發中，這是建議的寫法。
◦
它的效果通常就是 finish()（關閉目前頁面），但它比 finish() 更聰明，因為它會觸發所有你可能設定過的「返回邏輯」（例如：彈出「確定要離開嗎？」的對話框）。
•
return true： 告訴系統：「我已經處理好這個向上導航的動作了」。
3. 為什麼要寫這個？
通常在以下情況會用到它：
1.
確保行為一致：讓「標題列的箭頭」和「手機下方的返回鍵」做一模一樣的事情。
2.
Navigation 組件：如果你有使用 Android 的 Jetpack Navigation（導航組件），這個函數是必寫的，用來讓 Fragment 之間正確回跳。
💡 關鍵問題：它跟剛才的 onOptionsItemSelected 有什麼關係？
你可能會發現，這兩個函數都在處理「標題列返回鍵」：
1.
onOptionsItemSelected：
◦
它是處理「所有」選單項目的（包括標題列箭頭、右邊的三個點選單、自訂按鈕）。
◦
它是把箭頭當作一個 ID 為 android.R.id.home 的「選單項目」來處理。
2.
onSupportNavigateUp：
◦
它是專門處理「向上導航」這個動作的。
總結建議： 在你的程式碼中，這兩段其實功能重疊了。
•
如果你在 onOptionsItemSelected 裡寫了 if (item.itemId == android.R.id.home) { finish() }。
•
又在 onSupportNavigateUp 裡寫了 onBackPressedDispatcher.onBackPressed()。
這就像是幫門裝了兩道鎖，雖然不衝突，但通常只要選一種方式寫清楚即可。現代的寫法通常傾向於使用 onBackPressedDispatcher，因為它最符合使用者對「返回」的預期。


     */
//    // 處理返回按鈕點擊
//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressedDispatcher.onBackPressed()
//        return true
//    }


    //Theater Version:
    /**
    •
    這是當您的選項選單（Options Menu）中的任何項目被選中時會被調用的鉤子（Hook）函數。
    　
    特別處理了操作列（Action Bar）上的「向上/首頁」（Up/Home）按鈕，點擊後會結束（finish）目前的 Activity。
    　
    @param item 被選中的選單項目。
    　
    @return boolean 返回 false 以允許正常的選單處理繼續進行，返回 true 則表示在此處已處理完畢（不再向下傳遞）。
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 1. 先檢查是不是「返回鍵」
        if (item.itemId == android.R.id.home) {
            finish() // 如果是，關閉視窗
            // 注意：這裡沒有 return true，所以它會繼續往下走
        }

        // 2. 如果不是返回鍵，或是返回鍵處理完後，
        // 把控制權交給父類別，讓系統決定接下來要做什麼
        return super.onOptionsItemSelected(item)
    }


}