package com.example.intent_5

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btnSelect: Button
    private lateinit var tvDrink: TextView
    private lateinit var tvSugar: TextView
    private lateinit var tvIce: TextView

    private val ACTCODE = 100

    /*
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val bundle = data?.extras
                if (bundle != null) {
                    tvDrink.text = getString(R.string.drink_label, bundle.getString("drink"))
                    tvSugar.text = getString(R.string.sugar_label, bundle.getString("sugar"))
                    tvIce.text = getString(R.string.ice_label, bundle.getString("ice"))
                }
            }
        }
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvDrink = findViewById(R.id.tv_drink)
        tvSugar = findViewById(R.id.tv_sugar)
        tvIce = findViewById(R.id.tv_ice)
        btnSelect = findViewById<Button>(R.id.btn_select)

        btnSelect.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            // startForResult.launch(intent)
            startActivityForResult(intent, ACTCODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == ACTCODE && resultCode == RESULT_OK) {
            data?.let {
                tvDrink.text = getString(R.string.drink_label, it.getStringExtra("drink"))
                tvSugar.text = getString(R.string.sugar_label, it.getStringExtra("sugar"))
                tvIce.text = getString(R.string.ice_label, it.getStringExtra("ice"))
            }
        }
    }
}
