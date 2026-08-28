package com.example.fragment_1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var textViewMinnData: TextView
    private lateinit var buttonSwitch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d("main", "onCreate-M")
        textViewMinnData = findViewById<TextView>(R.id.textView_mainData)
        textViewMinnData.text = "It is sunny day."

        var flag = true
        buttonSwitch = findViewById<Button>(R.id.button_main)
        buttonSwitch.setOnClickListener {
            if (flag) {
                textViewMinnData.text = "It is main Activity."
                flag = false
            } else {
                textViewMinnData.text = "It is sunny day."
                flag = true
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("main", "onStart-M")
    }

    override fun onResume() {
        super.onResume()
        Log.d("main", "onResume-M")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("main", "onRestart-M")
    }

    override fun onPause() {
        super.onPause()
        Log.d("main", "onPause-M")
    }

    override fun onStop() {
        super.onStop()
        Log.d("main", "onStop-M")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("main", "onDestroy-M")
    }
}
