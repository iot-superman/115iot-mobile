package com.example.intent2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import java.util.Locale

class BMIActivity : AppCompatActivity() {
    private lateinit var buttonFinish: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bmiactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textView = findViewById<TextView>(R.id.textView)
        buttonFinish = findViewById<Button>(R.id.button_finish)

        // get intent data
        val name = intent.getStringExtra("name") ?: "someone"
        val height = intent.getIntExtra("height", 100)
        val weight = intent.getIntExtra("weight", 80)

        val bmi = weight / ((height / 100.0) * (height / 100.0))
        val formattedBmi = String.format(Locale.US, "%.2f", bmi)
        textView.text = "Name: $name\nHeight: $height\nWeight: $weight\nBMI: $formattedBmi"

        buttonFinish.setOnClickListener {


            Log.d("main", "[BMI] Name:" +textView.text)
            finish()
        }

    }
}