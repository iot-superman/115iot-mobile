package com.example.intent2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class BmiFloatActivity : AppCompatActivity() {
    private lateinit var buttonFloat: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bmi_float)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textView = findViewById<TextView>(R.id.textView_floatData)
        buttonFloat = findViewById<Button>(R.id.button_floatBack)
        val bundle = intent.extras
        if (bundle != null) {

//            val name = bundle.getString("name") ?: "someone"
//            val height = bundle.getFloat("height", 100.0f)
//            val weight = bundle.getFloat("weight", 78.0f)
//            val bmi = weight / ((height / 100.0) * (height / 100.0))
//            val formattedBmi = String.format(Locale.US, "%.2f", bmi)
//            textView.text = "Name: $name\nHeight: $height\nWeight: $weight\nBMI: $formattedBmi"


            //老師版：
            textView.text = ""
            val bundle = intent.extras
            val name = bundle?.getString("name")
            val height = bundle?.getFloat("height", 10.0f)
            val weight = bundle?.getFloat("weight", 1.0f)

            Log.d("main", "Float name = $name")
            Log.d("main", "Float height = $height")
            Log.d("main", "Float weight = $weight")

            val heightValue = height as Float
            val weightValue = weight as Float

            textView.append("name : $name\n")
            textView.append("height = $height\n")
            textView.append("weight = $weight\n")

            val bmi = (weightValue * 100.0 * 100.0) / (heightValue * heightValue)
            textView.append("bmi value = ${"%.2f".format(bmi)}")
        }
        buttonFloat.setOnClickListener {
            finish()
        }



    }
}