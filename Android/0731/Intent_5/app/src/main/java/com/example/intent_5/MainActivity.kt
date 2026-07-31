package com.example.intent_5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


val drinkCode = 100
class MainActivity : AppCompatActivity() {

    private lateinit var getResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn_select = findViewById<Button>(R.id.btn_select)
        val tv_drink = findViewById<TextView>(R.id.tv_drink)
        val tv_sugar = findViewById<TextView>(R.id.tv_sugar)
        val tv_ice = findViewById<TextView>(R.id.tv_ice)

        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("main", "code = ${result.resultCode}")
            if (result.resultCode == drinkCode) {
                val intent = result.data
                if (intent != null) {
                    val drink = intent.getStringExtra("drink")
                    val sugar = intent.getStringExtra("sugar")
                    val ice = intent.getStringExtra("ice")
                    tv_drink.text = getString(R.string.drink_label, drink)
                    tv_sugar.text = getString(R.string.sugar_label, sugar)
                    tv_ice.text = getString(R.string.ice_label, ice)
                }
            }
        }

        btn_select.setOnClickListener {
            val intent = Intent(this, DrinkActivity::class.java)
            getResult.launch(intent)
        }
    }
}
