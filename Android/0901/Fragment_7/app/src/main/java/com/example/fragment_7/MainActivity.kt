package com.example.fragment_7

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var textViewMainData: TextView
    private lateinit var buttonDlg2: Button
    private lateinit var buttonDlg1: Button

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        buttonDlg1 = findViewById<Button>(R.id.button_dlg1)
        buttonDlg2 = findViewById<Button>(R.id.button_dlg2)
        textViewMainData = findViewById<TextView>(R.id.textView_mainData)

        buttonDlg1.setOnClickListener {
            val dialog = MyDialog_1()
            dialog.show(supportFragmentManager, "MyDialog_1")
        }

        buttonDlg2.setOnClickListener {
            val dialog = MyDialog_2 { data:String ->
                textViewMainData.text = data
            }
            dialog.show(supportFragmentManager, "MyDialog_2")
        }

    }
}