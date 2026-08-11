package com.example.menu5

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var spinner2: Spinner
    private lateinit var spinner1: Spinner
    private lateinit var textViewResult: TextView
    private lateinit var numberPicker1: NumberPicker
    private lateinit var numberPicker2: NumberPicker

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
        numberPicker1 = findViewById(R.id.numberPicker1)
        numberPicker2 = findViewById(R.id.numberPicker2)

        val countryArray = resources.getStringArray(R.array.country)

        numberPicker1.minValue = 0
        numberPicker1.maxValue = 100
        numberPicker1.value = 5

        numberPicker2.minValue = 0
        numberPicker2.maxValue = countryArray.size - 1
        numberPicker2.displayedValues = countryArray
        numberPicker2.value = 0

        numberPicker2.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener {
            override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
                textViewResult.text = "picker 2 = ${countryArray[newVal]}"
            }
        })

        spinner1 = findViewById<Spinner>(R.id.spinner_1)
        spinner2 = findViewById<Spinner>(R.id.spinner_2)

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = parent?.getItemAtPosition(position).toString()
                textViewResult.text = "Spinner 1 = $item"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // val adapter = ArrayAdapter.createFromResource(this@MainActivity, R.array.country,
        //     android.R.layout.simple_spinner_item)

        val adapter = ArrayAdapter.createFromResource(this@MainActivity, R.array.country,
            R.layout.simple_spinner_item)

        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter
        spinner1.adapter = adapter

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = parent?.getItemAtPosition(position).toString()
                textViewResult.text = "Spinner 2 = $item"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}
