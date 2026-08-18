package com.example.listview3

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_display)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageView = findViewById<ImageView>(R.id.imageView_displayPic)
        val textView = findViewById<TextView>(R.id.textView_displayName)
        val buttonBack = findViewById<Button>(R.id.button_back)

        // 從 Intent 获取数据
        val flowerName = intent.getStringExtra("FLOWER_NAME")
        val flowerPic = intent.getIntExtra("FLOWER_PIC", R.drawable.flower1) // 默认为 flower1

        // 显示数据
        textView.text = flowerName
        imageView.setImageResource(flowerPic)

        buttonBack.setOnClickListener {
            finish() // 返回上一个页面
        }
    }
}