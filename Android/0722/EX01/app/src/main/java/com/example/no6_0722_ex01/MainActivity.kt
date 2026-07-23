package com.example.no6_0722_ex01

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
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

        // 取得 UI 元件
        val imageView = findViewById<ImageView>(R.id.imageView)
        val textView = findViewById<TextView>(R.id.textView)

        val btn1 = findViewById<ImageButton>(R.id.imageButton1)
        val btn2 = findViewById<ImageButton>(R.id.imageButton2)
        val btn3 = findViewById<ImageButton>(R.id.imageButton3)
        val btn4 = findViewById<ImageButton>(R.id.imageButton4)
        val btn5 = findViewById<ImageButton>(R.id.imageButton5)
        val btn6 = findViewById<ImageButton>(R.id.imageButton6)

        // 設定按鈕點擊事件
        btn1.setOnClickListener {
            imageView.setImageResource(R.drawable.img1)
            textView.text = "說明：花之1"
        }
        btn2.setOnClickListener {
            imageView.setImageResource(R.drawable.img2)
            textView.text = "說明：花之2"
        }
        btn3.setOnClickListener {
            imageView.setImageResource(R.drawable.img3)
            textView.text = "說明：花之3"
        }
        btn4.setOnClickListener {
            imageView.setImageResource(R.drawable.I4)
            textView.text = "說明：花之4"
        }
        btn5.setOnClickListener {
            imageView.setImageResource(R.drawable.I5)
            textView.text = "說明：花之5"
        }
        btn6.setOnClickListener {
            imageView.setImageResource(R.drawable.I6)
            textView.text = "說明：花之6"
        }
    }
}
