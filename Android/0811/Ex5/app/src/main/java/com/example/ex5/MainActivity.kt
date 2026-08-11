package com.example.ex5

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * EX-5 主畫面。
 *
 * 功能說明：
 * 1. 讓使用者選擇城市與生日（月份、日期）。
 * 2. 按下「確定」後顯示目前選擇內容。
 * 3. 透過右上角選單將資料儲存到 SharedPreferences。
 * 4. 透過右上角選單從 SharedPreferences 讀取並回填畫面。
 */
class MainActivity : AppCompatActivity() {

    /** 城市下拉選單（Spinner）。 */
    private lateinit var spinnerCity: Spinner

    /** 月份選擇器（1~12）。 */
    private lateinit var npMonth: NumberPicker

    /** 日期選擇器（1~31）。 */
    private lateinit var npDay: NumberPicker

    /** 確定按鈕，顯示目前選擇結果。 */
    private lateinit var btnOk: Button

    /** 顯示訊息與結果的文字區塊。 */
    private lateinit var tvResult: TextView

    /** 本地端儲存：保存城市索引、月份、日期。 */
    private lateinit var sharedPreferences: SharedPreferences

    /**
     * Activity 建立時初始化 UI 與事件。
     *
     * @param savedInstanceState 系統提供的先前狀態（可能為 null）。
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 讓內容避開系統狀態列/導覽列，避免被遮住。
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 設定工具列（標題與右上角選單圖示）。
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "EX-5"
        toolbar.overflowIcon = ContextCompat.getDrawable(this, R.drawable.ic_menu_white)

        // 綁定 UI 元件。
        spinnerCity = findViewById(R.id.spinnerCity)
        npMonth = findViewById(R.id.npMonth)
        npDay = findViewById(R.id.npDay)
        btnOk = findViewById(R.id.btnOk)
        tvResult = findViewById(R.id.tvResult)

        // 設定日期範圍（此範例固定 1~31，未依月份自動調整天數）。
        npMonth.minValue = 1
        npMonth.maxValue = 12
        npDay.minValue = 1
        npDay.maxValue = 31

        // 取得 SharedPreferences 實例，檔名為 CustomerData。
        sharedPreferences = getSharedPreferences("CustomerData", Context.MODE_PRIVATE)

        // 顯示目前使用者所選擇的城市與生日。
        btnOk.setOnClickListener {
            val city = spinnerCity.selectedItem.toString()
            val month = npMonth.value
            val day = npDay.value
            tvResult.text = "目前選擇：\n城市：$city\n生日：$month 月 $day 日"
        }
    }

    /**
     * 建立右上角選單。
     *
     * @param menu 要被填入選單項目的 Menu。
     * @return true 代表顯示此選單。
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /**
     * 處理右上角選單點擊事件。
     *
     * - action_save：儲存目前選擇。
     * - action_get：讀取已儲存資料並更新畫面。
     *
     * @param item 使用者點擊的選單項目。
     * @return 是否已處理該事件。
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                saveData()
                true
            }
            R.id.action_get -> {
                getData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * 將目前畫面上的選擇資料存入 SharedPreferences。
     *
     * 儲存鍵值：
     * - city_pos：Spinner 選取位置（Int）
     * - month：月份（Int）
     * - day：日期（Int）
     */
    private fun saveData() {
        val city = spinnerCity.selectedItemPosition
        val month = npMonth.value
        val day = npDay.value

        val editor = sharedPreferences.edit()
        editor.putInt("city_pos", city)
        editor.putInt("month", month)
        editor.putInt("day", day)
        editor.apply() // 非同步寫入，效能較佳。

        tvResult.text = "資料已儲存到 SharedPreferences"
    }

    /**
     * 從 SharedPreferences 讀取資料並回填到 UI。
     *
     * 若找不到已儲存值，預設為：
     * - city_pos = 0
     * - month = 1
     * - day = 1
     */
    private fun getData() {
        val cityPos = sharedPreferences.getInt("city_pos", 0)
        val month = sharedPreferences.getInt("month", 1)
        val day = sharedPreferences.getInt("day", 1)

        spinnerCity.setSelection(cityPos)
        npMonth.value = month
        npDay.value = day

        val cityStr = spinnerCity.adapter.getItem(cityPos).toString()
        tvResult.text = "從 SharedPreferences 讀取資料：\n城市：$cityStr\n生日：$month 月 $day 日"
    }
}