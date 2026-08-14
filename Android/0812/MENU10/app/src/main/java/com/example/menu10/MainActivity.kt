package com.example.menu10

import android.content.res.TypedArray
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var picArray: TypedArray
    private lateinit var nameAarry: Array<String>
    private lateinit var listViewFlower: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        listViewFlower = findViewById<ListView>(R.id.listView_flower)

        nameAarry = resources.getStringArray(R.array.flower_name)
        picArray = resources.obtainTypedArray(R.array.flower_pic)

        listDasta = mutableListOf<MutipleItem<String,Any>>()
        for(i in 0 until nameAarry.size){
            val data = MutipleItem(nameAarry[i])
            data.put("name", nameAarry[i]))
            data.put("pic", picArray.getResourceId(i,R.drawable.flower01))
            listDasta.add(data)
        }

    }
}