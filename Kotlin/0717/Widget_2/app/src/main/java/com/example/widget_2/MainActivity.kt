package com.example.widget_2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var button_reset: Button
    private lateinit var button_minus: Button
    private lateinit var button_plus: Button
    private lateinit var textViewCounter: TextView
//    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewCounter = findViewById<TextView>(R.id.txt_CountValue)
        button_plus = findViewById< Button>(R.id.button_plus)
        button_minus = findViewById<Button>(R.id.button_minus)
        button_reset = findViewById<Button>(R.id.button_reset)

        button_plus.setOnClickListener(MyButton())
        button_minus.setOnClickListener(MyButton())
        button_reset.setOnClickListener(MyButton())

    }

    inner class MyButton : View.OnClickListener{  //inner  內部類別，才可讀到 MainActivity 的變數
        override fun onClick(v: View?) {
            when(v?.id){
                R.id.button_plus -> {
                    var data = textViewCounter.text.toString()  //TEXT serials TO STRING
                    var number = data.toInt()
                    number++
                    textViewCounter.text = number.toString()
                }
                R.id.button_minus -> {
                    var data = textViewCounter.text.toString()
                    var number = data.toInt()
                    if(number > 0)
                        number--
                    textViewCounter.text = number.toString()
                }
                R.id.button_reset -> {
                    var number = 0
                    textViewCounter.text = number.toString()
                }
            }
        }
    }


}


