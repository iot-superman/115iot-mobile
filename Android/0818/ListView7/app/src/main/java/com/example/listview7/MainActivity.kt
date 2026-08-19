package com.example.listview7

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
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

        // 從資源檔讀取名稱與圖片陣列
        val nameArray = resources.getStringArray(R.array.nameArray)
        val picArray = resources.obtainTypedArray(R.array.picArray)
        val dataList = mutableListOf<SpinnerItem>()

        for (i in 0 until nameArray.size) {
            val resId = picArray.getResourceId(i, R.drawable.icecream)
            val data = SpinnerItem(nameArray[i], resId)
            dataList.add(data)
        }
        picArray.recycle()

        Log.d("main", "data list = $dataList")

        val spinnerData = findViewById<Spinner>(R.id.spinnerData)
        val textViewData = findViewById<TextView>(R.id.textViewData)

        val adapter = SpinnerArrayAdapter(this, dataList)
        spinnerData.adapter = adapter

        spinnerData.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position) as SpinnerItem
                textViewData.text = "Select : ${item.name}"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}
