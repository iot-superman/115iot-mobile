//https://chatgpt.com/s/m_6a840fa4d51881918ba37cc3810692e5
package com.example.listview6

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//Teacher Version:
        val gridViewData = findViewById<GridView>(R.id.gridView_id)
        val imageViewPic = findViewById<ImageView>(R.id.ImageView_pic)

        val nameArray = resources.getStringArray(R.array.name)
        val picArray = resources.obtainTypedArray(R.array.picture)

        val listData = mutableListOf<MutableMap<String, Any>>()

        for (i in 0 .. nameArray.size - 1) {
            val data = mutableMapOf<String, Any>()
            data.put("name", nameArray[i])
            // 從資源陣列中取得圖片 ID，若無則預設為 flower1
            data.put("pic", picArray.getResourceId(i, R.drawable.flower1))
            listData.add(data)
        }
        picArray.recycle()

        Log.d("main", "list data = $listData")

        
        /**
         * 建立自訂的 `MyAdapter`，將 `listData` 內的名稱與圖片資料
         * 轉換成 `GridView` 可以顯示的每一個格子項目。
         *
         * - `this@MainActivity`：提供 Adapter 所需的畫面內容與資源存取環境（Context）。
         * - `listData`：作為 Adapter 的資料來源，包含每筆要顯示的名稱與圖片資源。
         */
        // 使用自訂的 Adapter
        val adapter = MyAdapter(myContext = this@MainActivity, listData = listData)

        gridViewData.adapter = adapter

        // 設定 GridView 項目的點擊監聽器
        gridViewData.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 從 Adapter 中取得點擊位置的資料物件
                val item = parent?.getItemAtPosition(position) as MutableMap<String, Any>
                // 將下方的大圖更新為點選的圖片
                imageViewPic.setImageResource(item["pic"] as Int)
            }
        }
    }
}