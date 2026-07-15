package com.example.layout2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var buttonFlag1: Boolean = true
    private var buttonFlag2: Boolean = true

    private lateinit var buttonText2: Button
    private lateinit var buttonText1: Button
    private lateinit var textView3_Content: TextView
    private lateinit var textView2_Center: TextView
    private lateinit var textView1_Title: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textView1_Title = findViewById(R.id.textView_text1)
        textView2_Center = findViewById(R.id.textView_text2)
        textView3_Content = findViewById(R.id.textView_text3)
        
        val data1 = textView1_Title.text
        val data2 = textView2_Center.text
        textView3_Content.text = "$data1 \n$data2 \n\n"
        textView3_Content.append(data1.toString()+"\n"+data2.toString())

        buttonFlag1 = true
        buttonFlag2 = true

        buttonText1 =findViewById<Button>(R.id.button)
        buttonText1.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                if (buttonFlag1) {
                    textView1_Title.text = "HI good afernoon"
                    buttonFlag1 = false
                } else {
                    textView1_Title.text = data1
                    buttonFlag1 = true
                }
            }

        })
        buttonText2 =findViewById<Button>(R.id.button2)
        buttonText2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (buttonFlag2) {
                    textView2_Center.text = "It is sunny day"
                    buttonFlag2 = false
                } else {
                    textView2_Center.text = data2
                    buttonFlag2 = true
                }
            }
        })



    }
}
