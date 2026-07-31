package com.example.ex4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val citycode = 200
    private lateinit var rgCity: RadioGroup
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide the ActionBar for MainActivity
        supportActionBar?.hide()

        // Initialize views
        rgCity = findViewById(R.id.rgCity)
        tvResult = findViewById(R.id.tvResult)
        val btnOk = findViewById<Button>(R.id.btnOk)

        btnOk.setOnClickListener {
            val selectedId = rgCity.checkedRadioButtonId
            if (selectedId != -1) {
                val radioButton = findViewById<RadioButton>(selectedId)
                val cityName = radioButton.text.toString()

                val intent = Intent(this, CityActivity::class.java)
                intent.putExtra("CITY_NAME", cityName)
                // Use the requested "teacher style" startActivityForResult
                startActivityForResult(intent, citycode)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == citycode && resultCode == RESULT_OK) {
            val cityName = data?.getStringExtra("CITY_NAME") ?: ""
            val selectedFoods = data?.getStringExtra("SELECTED_FOODS") ?: ""
            
            tvResult.text = "城市名稱：$cityName\n美食建議：$selectedFoods"
        }
    }
}