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

class Activity_2 : AppCompatActivity() {
    private val ACT2_Code: Int = 200

    private lateinit var textViewData: TextView
    private lateinit var editTextInput: EditText
    private lateinit var buttonBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewData = findViewById(R.id.textView_act2Data)
        editTextInput = findViewById(R.id.editText_act2Input)
        buttonBack = findViewById(R.id.button_act2Back)

        val data = intent.getStringExtra("data")
        textViewData.text = data

        buttonBack.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("act2_data", editTextInput.text.toString())
            setResult(ACT2_Code, resultIntent)
            finish()
        }
    }
}
