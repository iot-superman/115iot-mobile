package com.example.menu8

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

/**
 * 時間選擇活動類
 * 用於顯示和選擇時間，支持時間選擇器組件和時間選擇對話框
 */
class TimeActivity : AppCompatActivity() {
    /** 日期時間日曆實例 */
    private lateinit var now: Calendar

    /** 時間選擇器組件 */
    private lateinit var timePicker: TimePicker

    /** 顯示選定時間的文本視圖 */
    private lateinit var textViewData: TextView

    /**
     * 活動創建時的初始化方法
     * 設置UI組件、初始化時間選擇器和監聽器
     * @param savedInstanceState 保存的實例狀態
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_time)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        textViewData = findViewById<TextView>(R.id.textView2)
        timePicker = findViewById<TimePicker>(R.id.timePicker_id)
        var btn_time = findViewById<Button>(R.id.button_time)

        // 初始化日曆並獲取當前時間
        now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val minute = now.get(Calendar.MINUTE)
        
        // 在文本視圖上顯示當前時間
        textViewData.text = "$hour:$minute"
        
        // 設置 24 小時制（視需求而定）
        timePicker.setIs24HourView(true)
        
        // 設置時間選擇器變更監聽器
        btn_time.setOnClickListener {
            // 更新文本視圖顯示選定的時間
            textViewData.text = "Selected time: $hour:$minute"
            // 創建時間選擇對話框實例，使用 MyTime 監聽器處理選擇結果
            val timeDialog = TimePickerDialog(this@TimeActivity, MyTime(), hour, minute, true)
            // 設置對話框提示信息
            timeDialog.setMessage("Select time")
            // 顯示對話框
            timeDialog.show()
        }
    }

    /**
     * 時間選擇器對話框的回調監聽器實現類
     */
    inner class MyTime : TimePickerDialog.OnTimeSetListener {
        /**
         * 當用戶選擇時間後調用此方法
         * @param view 時間選擇器視圖
         * @param hourOfDay 選中的小時（0-23）
         * @param minute 選中的分鐘（0-59）
         */
        override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
            textViewData.text = "Selected time: $hourOfDay:$minute"
        }
    }
}
