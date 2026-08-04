package com.example.menu3

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.setup, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val textViewData = findViewById<TextView>(R.id.textView)
        val imageViewPic = findViewById<ImageView>(R.id.imageView)

        when (item.itemId) {
            R.id.menu_flower -> {
                textViewData.text = "It is flower"
                imageViewPic.setImageResource(R.drawable.flower_1)
            }
            R.id.menu_cat1 -> {
                textViewData.text = "It is Cat 1"
                imageViewPic.setImageResource(R.drawable.cat_1)
            }
            R.id.menu_cat2 -> {
                textViewData.text = "It is Cat 2"
                imageViewPic.setImageResource(R.drawable.cat_2)
            }
            R.id.menu_cat3 -> {
                textViewData.text = "It is Cat 3"
                imageViewPic.setImageResource(R.drawable.cat_3)
            }
            R.id.menu_dog1 -> {
                textViewData.text = "It is Dog 1"
                imageViewPic.setImageResource(R.drawable.dog_1)
            }
            R.id.menu_dog2 -> {
                textViewData.text = "It is Dog 2"
                imageViewPic.setImageResource(R.drawable.dog_2)
            }
            R.id.menu_dog3 -> {
                textViewData.text = "It is Dog 3"
                imageViewPic.setImageResource(R.drawable.dog_3)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}