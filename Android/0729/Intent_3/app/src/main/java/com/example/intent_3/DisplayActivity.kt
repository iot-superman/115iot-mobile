package com.example.intent_3

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_display)

        // Handle window insets
        val rootView = findViewById<android.view.View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cityName = intent.getStringExtra("CITY_NAME")
        val imageResId = intent.getIntExtra("IMAGE_RES_ID", 0)
        val descriptionResId = intent.getIntExtra("DESC_RES_ID", 0)

        // Use the system ActionBar
        supportActionBar?.apply {
            title = cityName
            setDisplayHomeAsUpEnabled(true)
        }

        val imageView: ImageView = findViewById(R.id.displayImage)
        val textView: TextView = findViewById(R.id.displayText)

        if (imageResId != 0) {
            imageView.setImageResource(imageResId)
        }
        
        if (descriptionResId != 0) {
            textView.setText(descriptionResId)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
