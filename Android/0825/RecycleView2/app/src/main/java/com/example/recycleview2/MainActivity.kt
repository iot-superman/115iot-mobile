package com.example.recycleview2

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var textViewName: TextView
    private lateinit var imageViewPic: ImageView
    private lateinit var recyclerViewData: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageViewPic = findViewById(R.id.imageView_mainPic)
        textViewName = findViewById(R.id.textView_mainName)
        textViewName.text = "Flower 1"

        val nameArray = resources.getStringArray(R.array.name)
        val picArray = resources.obtainTypedArray(R.array.picture)

        val dataList = mutableListOf<MutableMap<String, Any>>()
        for (i in 0..nameArray.size - 1) {
            val data = mutableMapOf<String, Any>()
            data.put("name", nameArray[i])
            // 使用 flower1 因為專案中實際的檔案名稱是 flower1.png
            data.put("pic", picArray.getResourceId(i, R.drawable.flower1))
            dataList.add(data)
        }
        picArray.recycle()

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewData = findViewById(R.id.recyclerView_id)
        recyclerViewData.layoutManager = layoutManager

        val adapter = MyAdapter(this, dataList) { itemData: MutableMap<String, Any> ->
            Log.d("main", "main click = $itemData")
            textViewName.text = itemData.get("name").toString()
            imageViewPic.setImageResource(itemData.get("pic") as Int)
        }
        recyclerViewData.adapter = adapter
    }
}
