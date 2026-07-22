package com.example.widget_9

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var textResult: TextView
    private lateinit var buttonOK: Button
    private lateinit var radiobuttonKotlin: RadioButton
    private lateinit var radiobuttonJava: RadioButton
    private lateinit var radioGroupClass: RadioGroup
    private lateinit var radiobuttonWed: RadioButton
    private lateinit var radiobuttonTue: RadioButton
    private lateinit var radiobuttonMon: RadioButton
    private lateinit var radioGroupWeek: RadioGroup

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

         radioGroupWeek  = findViewById<RadioGroup>(R.id.radioGroup_Week)
         radiobuttonMon = findViewById<RadioButton>(R.id.radioButton_mon)
         radiobuttonTue = findViewById<RadioButton>(R.id.radioButton_tue)
         radiobuttonWed = findViewById<RadioButton>(R.id.radioButton_wed)


        radioGroupClass = findViewById<RadioGroup>(R.id.radioGroup_Classs)
        radiobuttonJava = findViewById<RadioButton>(R.id.radioButton_Java)
        radiobuttonKotlin = findViewById<RadioButton>(R.id.radioButton_Kotlin)

        buttonOK = findViewById<Button>(R.id.button_OK)
        textResult = findViewById<TextView>(R.id.textView_Result)
        textResult.text = ""

        buttonOK.setOnClickListener {
            when(radioGroupWeek.checkedRadioButtonId) {
                R.id.radioButton_mon -> {
                    textResult.text = "Monday"
                }

                R.id.radioButton_tue -> {
                    textResult.text = "Tuesday"
                }

                R.id.radioButton_wed -> {
                    textResult.text = "Wednesday"
                }
            }

            when(radioGroupClass.checkedRadioButtonId){
                R.id.radioButton_Java -> {
                    textResult.append("\nclass ${radiobuttonKotlin.text}")
                }
                R.id.radioButton_Kotlin -> {
                    textResult.append("\nclass ${radiobuttonKotlin.text}")
                }

            }

        }

        radioGroupWeek.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                 radioGroupClass.clearCheck()
                when (checkedId) {
                    R.id.radioButton_mon -> {
                        radiobuttonJava.text = "Java"
                        radiobuttonKotlin.text = "Kotlin"
                    }
                    R.id.radioButton_tue -> {
                        radiobuttonJava.text = "HT66"
                        radiobuttonKotlin.text = "Digitasl_Logic"
                    }
                    R.id.radioButton_wed -> {
                        radiobuttonJava.text = "Python"
                        radiobuttonKotlin.text = "HTML"

                    }
                }
            }
        })







    }
}