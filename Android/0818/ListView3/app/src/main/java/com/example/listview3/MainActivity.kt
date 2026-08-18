package com.example.listview3

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var listViewData: ListView
    private lateinit var btnFlower: Button
    private lateinit var btnCat: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listViewData = findViewById(R.id.listView_data)
        btnFlower = findViewById(R.id.button_flower)
        btnCat = findViewById(R.id.button_cat)

        // 預設載入花朵資料
        loadData("flower")

        btnFlower.setOnClickListener {
            loadData("flower")
        }

        btnCat.setOnClickListener {
            loadData("cat")
        }
    }

    private fun loadData(type: String) {
        val nameArray: Array<String>
        val picArray: TypedArray

        if (type == "flower") {
            nameArray = resources.getStringArray(R.array.flower_names)
            picArray = resources.obtainTypedArray(R.array.flower_images)
        } else {
            nameArray = resources.getStringArray(R.array.cat_names)
            picArray = resources.obtainTypedArray(R.array.cat_images)
        }

        val listData = mutableListOf<Map<String, Any>>()

        for (i in nameArray.indices) {
            val data = mutableMapOf<String, Any>()
            data["name"] = nameArray[i]
            data["pic"] = picArray.getResourceId(i, R.drawable.flower1)
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

        listViewData.adapter = adapter

        listViewData.setOnItemClickListener { _, _, position, _ ->
            val flowerName = listData[position]["name"] as String
            val flowerPic = listData[position]["pic"] as Int

            val intent = Intent(this, DisplayActivity::class.java).apply {
                putExtra("FLOWER_NAME", flowerName)
                putExtra("FLOWER_PIC", flowerPic)
            }
            startActivity(intent)
        }
    }
}