package com.example.listview2

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * 主畫面 Activity。
 *
 * 此畫面會：
 * 1. 從資源檔讀取城市名稱與人口資料。
 * 2. 將資料整理成 `SimpleAdapter` 可使用的清單格式。
 * 3. 使用 `ListView` 顯示城市與人口。
 * 4. 當使用者點擊某個城市時，在下方 `TextView` 顯示所選城市的詳細資訊，
 *    並將文字顏色改為紅色。
 */
class MainActivity : AppCompatActivity() {

    /**
     * `ListView` 的資料來源。
     *
     * 每一筆 `Map<String, String>` 包含兩個欄位：
     * - `"city"`：城市名稱
     * - `"pop"`：人口數
     */
    private lateinit var listData: ArrayList<Map<String, String>>

    /**
     * 儲存人口字串陣列，資料來源為 `R.array.population`。
     */
    private lateinit var popArray: Array<String>

    /**
     * 儲存城市名稱字串陣列，資料來源為 `R.array.city`。
     */
    private lateinit var cityArray: Array<String>

    /**
     * 顯示城市清單的 `ListView`。
     */
    private lateinit var listViewCity: ListView

    /**
     * 顯示被點選城市詳細資訊的 `TextView`。
     */
    private lateinit var textViewData: TextView

    /**
     * Activity 建立時呼叫。
     *
     * 在此完成以下初始化工作：
     * - 啟用 Edge-to-Edge 顯示模式
     * - 載入版面配置
     * - 設定系統視窗邊距
     * - 綁定畫面元件
     * - 讀取資源陣列資料
     * - 建立清單資料
     * - 設定 `ListView` 的 Adapter
     * - 設定項目點擊事件
     *
     * @param savedInstanceState 若 Activity 曾被系統回收，則包含先前儲存的狀態資料
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 啟用畫面延伸到系統列（狀態列／導覽列）區域
        enableEdgeToEdge()

        // 載入主畫面版面配置
        setContentView(R.layout.activity_main)

        // 根據系統列 Insets 自動調整主容器的 padding，避免內容被狀態列或導覽列遮住
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 綁定顯示詳細資料的文字元件
        textViewData = findViewById(R.id.textView_data)

        // 綁定城市清單元件
        listViewCity = findViewById(R.id.listView_city)

        // 從字串資源讀取城市名稱陣列
        cityArray = resources.getStringArray(R.array.city)

        // 從字串資源讀取人口陣列
        popArray = resources.getStringArray(R.array.population)

        // 初始化要提供給 Adapter 使用的資料集合
        listData = ArrayList<Map<String, String>>()

        // 將城市與人口資料逐筆組成 Map，加入清單資料中
        for (i in 0..cityArray.size - 1) {
            val data = mutableMapOf<String, String>()
            data.put("city", cityArray[i])
            data.put("pop", popArray[i])
            listData.add(data)
        }

        // 輸出整理後的資料到 Logcat，方便除錯
        Log.d("main", "listData = $listData")

        /**
         * 建立 `SimpleAdapter`：
         * - `listData` 為資料來源
         * - `R.layout.item_layout` 為每一列的版面配置
         * - `"city"` 對應到 `R.id.textView_itemName`
         * - `"pop"` 對應到 `R.id.textView_itemNum`
         */
        val adapter = SimpleAdapter(
            this@MainActivity,
            listData,
            R.layout.item_layout,
            arrayOf("city", "pop"),
            intArrayOf(R.id.textView_itemName, R.id.textView_itemNum)
        )

        // 將 Adapter 指定給 ListView
        listViewCity.adapter = adapter

        // 設定清單項目點擊事件
        listViewCity.onItemClickListener = object : AdapterView.OnItemClickListener {

            /**
             * 當使用者點擊 `ListView` 某一列時呼叫。
             *
             * 會取得被點選項目的城市與人口資訊，並顯示在 `textViewData` 中，
             * 同時將文字顏色設為紅色。
             *
             * @param parent 觸發事件的 AdapterView
             * @param view 被點擊的項目 View
             * @param position 被點擊項目的索引位置
             * @param id 被點擊項目的資料列 ID
             */
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 取得被點擊的資料項目，並轉型為可讀取的 Map 結構
                val item = parent?.getItemAtPosition(position) as MutableMap<String, String>

                // 取出城市名稱
                val name = item["city"].toString()

                // 取出人口資料
                val pop = item["pop"].toString()

                // 將所選城市資訊顯示於畫面下方
                textViewData.text = "city name : $name\npopulation : $pop"

                // 將文字顏色設為紅色，讓使用者更容易注意到更新內容
                textViewData.setTextColor(Color.parseColor("#FF0000"))
            }
        }
    }
}