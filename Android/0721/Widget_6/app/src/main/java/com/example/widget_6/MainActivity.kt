package com.example.widget_6

import android.os.Bundle

import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var buttonOk: Button
    private lateinit var buttonCancel: Button
    private lateinit var editTextEngish: EditText
    private lateinit var editTextMath: EditText
    private lateinit var editTextID: EditText
    private lateinit var editTextName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editTextName = findViewById<EditText>(R.id.edtext_inputName)

        editTextID  =  findViewById<EditText>(R.id.edText_inputID)
        editTextMath = findViewById<EditText>(R.id.editText_MathScore)
        editTextEngish  = findViewById<EditText>(R.id.editText_EngScore)

        buttonCancel = findViewById<Button>(R.id.button_cancel)
        buttonOk = findViewById<Button>(R.id.button_ok)

        textView = findViewById<TextView>(R.id.textView_result)


        buttonCancel.setOnClickListener {
            editTextName.setText("")
            editTextID.setText("")
            editTextMath.setText("")
            editTextEngish.setText("")
            textView.setText("")
        }

        buttonOk.setOnClickListener {
            val name = editTextName.text.toString()
            val id = editTextID.text.toString()
            val mathStr = editTextMath.text.toString()
            val englishStr = editTextEngish.text.toString()

            val math = mathStr.toIntOrNull() ?: 0  //Null防呆防護
            val english = englishStr.toIntOrNull() ?: 0  //Null防呆防護

            textView.text = "Name: ${name}\nID: ${id}\nMath Score: ${math}\nEnglish Score: ${english}\n"
            textView.append("The Score sum = ${math + english}")
        }



    }
}