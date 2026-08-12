package com.example.listview3

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
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
        val listViewFlower =  findViewById<ListView>(R.id.listView_id)

        val nameArray = resources.getStringArray(R.array.flower_names)
        val picArray = resources.obtainTypedArray(R.array.flower_images)

        val listData = mutableListOf<MutableMap<String, Any>>()

        for (i in 0 .. nameArray.size - 1) {
            val data = mutableMapOf<String, Any>()
            data.put("name", nameArray[i])
            data.put("pic", picArray.getResourceId(i, R.drawable.flower1))
            listData.add(data)
        }

        picArray.recycle()

        Log.d("main", "list data = $listData")

        /**
         * 将花朵名称和图片数据绑定到自定义列表项布局中。
         */
        val adapter = SimpleAdapter(this, listData, R.layout.item_layout, arrayOf("name", "pic"), intArrayOf(R.id.textView_itemName, R.id.imageView_itemPic))

        listViewFlower.adapter = adapter

        /**
         * 当用户点击某一项时，显示被点击花朵的名称。
         */
        listViewFlower.setOnItemClickListener { _, _, position, _ ->
            val flowerName = nameArray[position]
            Toast.makeText(this, "You clicked: $flowerName", Toast.LENGTH_SHORT).show()
        }

    }
}