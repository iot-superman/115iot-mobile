package com.example.ex4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        val cityName = intent.getStringExtra("CITY_NAME") ?: ""
        val tvCityTitle = findViewById<TextView>(R.id.tvCityTitle)
        tvCityTitle.text = cityName

        val cbFood1 = findViewById<CheckBox>(R.id.cbFood1)
        val cbFood2 = findViewById<CheckBox>(R.id.cbFood2)
        val cbFood3 = findViewById<CheckBox>(R.id.cbFood3)
        val cbFood4 = findViewById<CheckBox>(R.id.cbFood4)
        val btnSelect = findViewById<Button>(R.id.btnSelect)

        // Dynamically change food names based on city (Optional demo)
        when (cityName) {
            "台北" -> {
                cbFood1.text = "牛肉麵"; cbFood2.text = "滷肉飯"
                cbFood3.text = "小籠包"; cbFood4.text = "芒果冰"
            }
            "新北" -> {
                cbFood1.text = "淡水阿給"; cbFood2.text = "九份芋圓"
                cbFood3.text = "深坑豆腐"; cbFood4.text = "萬里蟹"
            }
            "桃園" -> {
                cbFood1.text = "大溪豆乾"; cbFood2.text = "龍岡米干"
                cbFood3.text = "拉拉山水蜜桃"; cbFood4.text = "石門活魚"
            }
        }

        btnSelect.setOnClickListener {
            val selectedFoods = mutableListOf<String>()
            if (cbFood1.isChecked) selectedFoods.add(cbFood1.text.toString())
            if (cbFood2.isChecked) selectedFoods.add(cbFood2.text.toString())
            if (cbFood3.isChecked) selectedFoods.add(cbFood3.text.toString())
            if (cbFood4.isChecked) selectedFoods.add(cbFood4.text.toString())

            val resultIntent = Intent()
            resultIntent.putExtra("CITY_NAME", cityName)
            resultIntent.putExtra("SELECTED_FOODS", selectedFoods.joinToString(", "))
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}