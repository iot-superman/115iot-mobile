package com.example.listview3

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listView = findViewById(R.id.listView_id)
        val btnFlower = findViewById<Button>(R.id.button_flower)
        val btnCat = findViewById<Button>(R.id.button_cat)

        // 預設載入花卉資料
        loadListView(R.array.flower_names, R.array.flower_images)

        btnFlower.setOnClickListener {
            loadListView(R.array.flower_names, R.array.flower_images)
            Toast.makeText(this, "切換至花卉列表", Toast.LENGTH_SHORT).show()
        }

        btnCat.setOnClickListener {
            loadListView(R.array.cat_names, R.array.cat_images)
            Toast.makeText(this, "切換至貓咪列表", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 根据提供的资源 ID 加载 ListView 数据
     */
    private fun loadListView(namesResId: Int, imagesResId: Int) {
        val nameArray = resources.getStringArray(namesResId)
        val picArray = resources.obtainTypedArray(imagesResId)

        val listData = mutableListOf<MutableMap<String, Any>>()

        for (i in 0 until nameArray.size) {
            val data = mutableMapOf<String, Any>()
            data["name"] = nameArray[i]
            // 如果圖片資源不足，則循環使用或使用預設圖
            val resId = if (i < picArray.length()) {
                picArray.getResourceId(i, R.drawable.flower1)
            } else {
                R.drawable.flower1
            }
            data["pic"] = resId
            listData.add(data)
        }

        picArray.recycle()

        val adapter = SimpleAdapter(
            this,
            listData,
            R.layout.item_layout,
            arrayOf("name", "pic"),
            intArrayOf(R.id.textView_itemName, R.id.imageView_itemPic)
        )

        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedName = nameArray[position]
            Toast.makeText(this, "您點擊了: $selectedName", Toast.LENGTH_SHORT).show()
        }
    }
}
