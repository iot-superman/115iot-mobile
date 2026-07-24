package com.example.widget_10b

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var textViewResult: TextView
    private lateinit var buttonOK: Button
    private lateinit var chkbox3: CheckBox
    private lateinit var chkbox2: CheckBox
    private lateinit var chkbox1: CheckBox
    private lateinit var radiobtn3: RadioButton
    private lateinit var radiobtn2: RadioButton
    private lateinit var radiobtn1: RadioButton
    private lateinit var radioGGroupFlower: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        radioGGroupFlower = findViewById<RadioGroup>(R.id.radioGroup_Flower)
        radiobtn1 = findViewById<RadioButton>(R.id.radioButton_f1)
        radiobtn2 = findViewById<RadioButton>(R.id.radioButton_f2)
        radiobtn3 = findViewById<RadioButton>(R.id.radioButton_f3)

        chkbox1 = findViewById<CheckBox>(R.id.checkBox_1)
        chkbox2 = findViewById<CheckBox>(R.id.checkBox_2)
        chkbox3 = findViewById<CheckBox>(R.id.checkBox_3)
        buttonOK = findViewById<Button>(R.id.button)

        textViewResult = findViewById<TextView>(R.id.textView)
        textViewResult.text = ""

        buttonOK.setOnClickListener {
            textViewResult.text = ""
            when(radioGGroupFlower.checkedRadioButtonId){
                R.id.radioButton_f1 -> textViewResult.text = radiobtn1.text
                R.id.radioButton_f2 -> textViewResult.text = radiobtn2.text
                R.id.radioButton_f3 -> textViewResult.text = radiobtn3.text
            }

            textViewResult.append("\n")
            if (chkbox1.isChecked) textViewResult.append("${chkbox1.text} ,")
            if (chkbox2.isChecked) textViewResult.append("${chkbox2.text} ,")
            if (chkbox3.isChecked) textViewResult.append("${chkbox3.text} ,")



        }

    }
}