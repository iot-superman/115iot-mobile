package com.example.widget_3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageView = findViewById<ImageView>(R.id.imageView)
        val button1 = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)

        textView = findViewById<TextView>(R.id.textView)

        button1.setOnClickListener {
            imageView.setImageResource(R.drawable.img1)
            textView.text = "This is a flower 1"
        }

        button2.setOnClickListener {
            imageView.setImageResource(R.drawable.img2)
            textView.text = "This is a flower 2"
        }
        button3.setOnClickListener {
            imageView.setImageResource(R.drawable.img3)
            textView.text = "This is a flower 3"
        }
    }
}