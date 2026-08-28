package com.example.avitity_firebase

import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FirebaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_firebase)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listView = findViewById<ListView>(R.id.listview_id)
        val fab = findViewById<FloatingActionButton>(R.id.floatingButton_id)

        val data = ArrayList<Map<String, String>>()
        for (i in 1..20) {
            val item = HashMap<String, String>()
            item["title"] = "Item $i"
            item["subtitle"] = "Sub Item $i"
            data.add(item)
        }

        val adapter = SimpleAdapter(
            this,
            data,
            android.R.layout.simple_list_item_2,
            arrayOf("title", "subtitle"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )
        listView.adapter = adapter

        fab.setOnClickListener {
            Toast.makeText(this, "Floating button clicked", Toast.LENGTH_SHORT).show()
        }
    }
}