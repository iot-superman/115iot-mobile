package com.example.intent_3

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable Edge-to-Edge
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, systemBars.top, v.paddingRight, systemBars.bottom)
            insets
        }

        val btnTaipei: ImageButton = findViewById(R.id.btnTaipei)
        val btnNewTaipei: ImageButton = findViewById(R.id.btnNewTaipei)
        val btnTaoyuan: ImageButton = findViewById(R.id.btnTaoyuan)
        val btnHsinchu: ImageButton = findViewById(R.id.btnHsinchu)

        btnTaipei.setOnClickListener {
            startDisplayActivity(getString(R.string.taipei), R.drawable.taipei, R.string.taipei_desc)
        }

        btnNewTaipei.setOnClickListener {
            startDisplayActivity(getString(R.string.new_taipei), R.drawable.newtaipei, R.string.new_taipei_desc)
        }

        btnTaoyuan.setOnClickListener {
            startDisplayActivity(getString(R.string.taoyuan), R.drawable.taoyuan, R.string.taoyuan_desc)
        }

        btnHsinchu.setOnClickListener {
            startDisplayActivity(getString(R.string.hsinchu), R.drawable.hsinchu, R.string.hsinchu_desc)
        }
    }

    private fun startDisplayActivity(cityName: String, imageResId: Int, descResId: Int) {
        val intent = Intent(this, DisplayActivity::class.java).apply {
            putExtra("CITY_NAME", cityName)
            putExtra("IMAGE_RES_ID", imageResId)
            putExtra("DESC_RES_ID", descResId)
        }
        startActivity(intent)
    }
}
