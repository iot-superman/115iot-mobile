package com.example.intent_5

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.second_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etDrink = findViewById<EditText>(R.id.et_drink)
        val rgSugar = findViewById<RadioGroup>(R.id.rg_sugar)
        val rgIce = findViewById<RadioGroup>(R.id.rg_ice)
        val btnSend = findViewById<Button>(R.id.btn_send)

        btnSend.setOnClickListener {
            val drink = etDrink.text.toString()
            val sugar = findViewById<RadioButton>(rgSugar.checkedRadioButtonId).text.toString()
            val ice = findViewById<RadioButton>(rgIce.checkedRadioButtonId).text.toString()

            /*
            val bundle = Bundle()
            bundle.putString("drink", drink)
            bundle.putString("sugar", sugar)
            bundle.putString("ice", ice)
            */

            val intent = Intent()
            // intent.putExtras(bundle)
            intent.putExtra("drink", drink)
            intent.putExtra("sugar", sugar)
            intent.putExtra("ice", ice)

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
