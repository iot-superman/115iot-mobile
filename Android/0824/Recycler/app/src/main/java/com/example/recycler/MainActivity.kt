package com.example.recycler

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 主畫面 Activity。
 *
 * 負責初始化 RecyclerView，讀取名稱與圖片資源，
 * 並建立資料清單提供給 [CardAdapter] 顯示。
 */
class MainActivity : AppCompatActivity() {
    /**
     * 建立畫面並完成列表資料初始化。
     *
     * 流程包含：
     * 1. 套用 Edge-to-Edge 與系統列 Insets。
     * 2. 從資源檔讀取名稱與圖片。
     * 3. 組成資料清單並設定 RecyclerView。
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textViewData = findViewById<TextView>(R.id.textView_data)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val nameArray = resources.getStringArray(R.array.name)
        val dataList = mutableListOf<MutableMap<String, Any>>()

        // 將資源資料組成 Adapter 可使用的清單。
        for (i in 0 until nameArray.size) {
            val item = mutableMapOf<String, Any>()
            item["name"] = nameArray[i]
            
            // 直接根據檔名 "flower1", "flower2"... 獲取資源 ID
            val resName = "flower${i + 1}"
            val resId = resources.getIdentifier(resName, "drawable", packageName)
            
            // 如果成功獲取 ID 就使用它，否則使用預設的 R.drawable.flower1
            item["pic"] = if (resId != 0) resId else R.drawable.flower1
            
            dataList.add(item)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CardAdapter(this, dataList)
    }
}