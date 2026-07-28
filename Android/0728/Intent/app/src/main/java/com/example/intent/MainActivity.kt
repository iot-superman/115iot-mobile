package com.example.intent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var buttonAct1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        buttonAct1 = findViewById<Button>(R.id.button_mainAct1)
        buttonAct1.setOnClickListener {
            val intent1 = Intent(this@MainActivity, MainActivity2::class.java)
            startActivity(intent1)
        }


        Log.d("main", "onCreate()")

    }

    override fun onStart() {
        super.onStart()
        Log.d("main", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("main", "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("main", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("main", "onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("main", "onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("main", "onDestroy()")
    }
}