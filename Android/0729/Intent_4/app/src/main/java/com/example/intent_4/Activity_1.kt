package com.example.intent_4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity_1 : AppCompatActivity() {
    private val Act1Code: Int = 100

    private lateinit var textViewData: TextView
    private lateinit var editTextInput: EditText
    private lateinit var buttonBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewData = findViewById(R.id.textView3)
        editTextInput = findViewById(R.id.editTextText_input)
        buttonBack = findViewById(R.id.button2)

        val data = intent.getStringExtra("data")
        textViewData.text = data

        buttonBack.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("act1_data", editTextInput.text.toString())
            setResult(Act1Code, resultIntent)
            finish()
        }
    }
}
