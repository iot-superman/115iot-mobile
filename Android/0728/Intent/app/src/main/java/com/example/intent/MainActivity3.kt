package com.example.intent

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity3 : AppCompatActivity() {
    private lateinit var btn_back: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d("main", "act3-onCreate()")
        btn_back = findViewById<Button>(R.id.button_Act3Back)
        btn_back.setOnClickListener {
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("main", "act3-onStart()")
    }
    override fun onResume() {
        super.onResume()
        Log.d("main", "act3-onResume()")
    }
    override fun onPause() {
        super.onPause()
        Log.d("main", "act3-onPause()")
    }
    override fun onStop() {
        super.onStop()
        Log.d("main", "act2-onStop()")
    }
    override fun onRestart() {
        super.onRestart()
        Log.d("main", "act3-onRestart()")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("main", "act3-onDestroy()")
    }


}