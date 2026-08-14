package com.example.listview

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var listCity: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listCity = findViewById(R.id.listView_id)

        val city = resources.getStringArray(R.array.city)
        val adapter = ArrayAdapter<String>(this@MainActivity, R.layout.item_layout, R.id.textView_itemData, city)
        listCity.adapter = adapter

        listCity.setOnItemClickListener { parent, view, position, id ->
            val selectedCity = parent.getItemAtPosition(position).toString()
            Toast.makeText(this@MainActivity, "City: $selectedCity", Toast.LENGTH_SHORT).show()
        }
    }
}