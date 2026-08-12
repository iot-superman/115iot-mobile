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

class MainActivity : AppCompatActivity() {
    private lateinit var listData: ArrayList<Map<String, String>>
    private lateinit var popArray: Array<String>
    private lateinit var cityArray: Array<String>
    private lateinit var listViewCity: ListView
    private lateinit var textViewData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewData = findViewById(R.id.textView_data)
        listViewCity = findViewById(R.id.listView_city)

        cityArray = resources.getStringArray(R.array.city)
        popArray = resources.getStringArray(R.array.population)

        listData = ArrayList<Map<String, String>>()
        for (i in 0..cityArray.size - 1) {
            val data = mutableMapOf<String, String>()
            data.put("city", cityArray[i])
            data.put("pop", popArray[i])
            listData.add(data)
        }

        Log.d("main", "listData = $listData")

        val adapter = SimpleAdapter(
            this@MainActivity,
            listData,
            R.layout.item_layout,
            arrayOf("city", "pop"),
            intArrayOf(R.id.textView_itemName, R.id.textView_itemNum)
        )
        listViewCity.adapter = adapter

        listViewCity.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position) as MutableMap<String, String>
                val name = item["city"].toString()
                val pop = item["pop"].toString()
                textViewData.text = "city name : $name\npopulation : $pop"
                textViewData.setTextColor(Color.parseColor("#FF0000"))
            }
        }
    }
}
