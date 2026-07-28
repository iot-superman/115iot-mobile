package com.example.intent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    private lateinit var buttonAct3: Button
    private lateinit var buttonBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d("main", "act2-onCreate()")
        buttonBack = findViewById<Button>(R.id.btn_back)
        buttonBack.setOnClickListener {
            finish()
        }
        buttonAct3 = findViewById<Button>(R.id.button2)
        buttonAct3.setOnClickListener {
            val intent = Intent(this@MainActivity2, MainActivity3::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("main", "act2-onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("main", "act2-onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("main", "act2-onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("main", "act2-onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("main", "act2-onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("main", "act2-onDestroy()")
    }



}