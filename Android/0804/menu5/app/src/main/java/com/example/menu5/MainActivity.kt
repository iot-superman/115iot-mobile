package com.example.menu5

import android.os.Bundle
import android.widget.NumberPicker
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var textViewResult: TextView
    private lateinit var numberPicker: NumberPicker
    private lateinit var numberPicker1: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        

        textViewResult = findViewById(R.id.textView_result)
        numberPicker = findViewById(R.id.NumberPicker)
        numberPicker1 = findViewById(R.id.numberPicker_1)


        numberPicker.minValue = 0
        numberPicker.maxValue = 100
        numberPicker.value = 50

        numberPicker1.minValue = 0
        numberPicker1.maxValue = 100
        numberPicker1.value = 50

        updateResult()

        numberPicker.setOnValueChangedListener { _, _, _ ->
            updateResult()
        }
        numberPicker1.setOnValueChangedListener { _, _, _ ->
            updateResult()
        }
    }

    private fun updateResult() {
        textViewResult.text = getString(R.string.result_format, numberPicker.value, numberPicker1.value)
    }
}