package com.example.ex6

import android.app.Activity
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

class SelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_select)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val edDrink = findViewById<EditText>(R.id.ed_drink)
        val edQuantity = findViewById<EditText>(R.id.ed_quantity)
        val rgSugar = findViewById<RadioGroup>(R.id.rg_sugar)
        val rgIce = findViewById<RadioGroup>(R.id.rg_ice)
        val btnSend = findViewById<Button>(R.id.btn_send)

        // 接收傳入的飲料名稱
        val selectedDrink = intent.getStringExtra("selected_drink")
        edDrink.setText(selectedDrink)

        btnSend.setOnClickListener {
            if (edDrink.text.isEmpty() || edQuantity.text.isEmpty()) return@setOnClickListener

            val drink = edDrink.text.toString()
            val quantity = edQuantity.text.toString()
            val sugarId = rgSugar.checkedRadioButtonId
            val iceId = rgIce.checkedRadioButtonId

            if (sugarId == -1 || iceId == -1) return@setOnClickListener

            val sugar = findViewById<RadioButton>(sugarId).text.toString()
            val ice = findViewById<RadioButton>(iceId).text.toString()

            val intent = Intent()
            val bundle = Bundle()
            bundle.putString("drink", drink)
            bundle.putString("quantity", quantity)
            bundle.putString("sugar", sugar)
            bundle.putString("ice", ice)
            intent.putExtras(bundle)

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
