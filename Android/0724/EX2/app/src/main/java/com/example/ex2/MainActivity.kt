package com.example.ex2

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private fun calculateBMI(height: Double, weight: Double): Double {
        val heightInMeters = height / 100
        return weight / (heightInMeters * heightInMeters)
    }

    private lateinit var rbFemale: RadioButton
    private lateinit var rbMale: RadioButton
    private lateinit var rbgender: RadioGroup
    private lateinit var tvResult: TextView
    private lateinit var ivResult: ImageView
    private lateinit var btnBMI: Button
    private lateinit var btnCancel: Button
    private lateinit var weight: TextInputEditText
    private lateinit var height: TextInputEditText

    private lateinit var age: TextInputEditText
    private lateinit var name: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        /* find by id all widgets */
        name = findViewById(R.id.etName)
        age = findViewById(R.id.etAge)
        rbgender = findViewById(R.id.rgGender)
        rbMale = findViewById(R.id.rbMale)
        rbFemale = findViewById(R.id.rbFemale)

        height = findViewById(R.id.etHeight)
        weight = findViewById(R.id.etWeight)
        btnCancel = findViewById<Button>(R.id.btnCancel)
        btnBMI = findViewById<Button>(R.id.btnBMI)
        ivResult = findViewById<ImageView>(R.id.ivResult)
        tvResult = findViewById<TextView>(R.id.tvResult)

        btnCancel.setOnClickListener {
            name.setText("")
            age.setText("")
            rbgender.clearCheck()
            height.setText("")
            weight.setText("")
            tvResult.text = ""
            ivResult.setImageDrawable(null)
        }

        btnBMI.setOnClickListener {
            val nameStr = name.text.toString()
            val ageStr = age.text.toString()
            val heightStr = height.text.toString()
            val weightStr = weight.text.toString()

            if (nameStr.isNotEmpty() && ageStr.isNotEmpty() && heightStr.isNotEmpty() && weightStr.isNotEmpty()) {
                val bmi = calculateBMI(heightStr.toDouble(), weightStr.toDouble())
                val result = when {
                    bmi < 18.5 -> "過輕"
                    bmi < 24.0 -> "正常"
                    bmi < 27.0 -> "過重"
                    else -> "肥胖"
                }

                val gender = when (rbgender.checkedRadioButtonId) {
                    R.id.rbMale -> "男"
                    R.id.rbFemale -> "女"
                    else -> "未指定"
                }

                tvResult.text = "Name: $nameStr\nAge: $ageStr\nSex: $gender\nBMI: ${String.format("%.2f", bmi)}\nBMI 結果: $result"

                if (rbMale.isChecked) {
                    when {
                        bmi < 18.5 -> ivResult.setImageResource(R.drawable.men_fat3)
                        bmi < 24.0 -> ivResult.setImageResource(R.drawable.men_fat4)
                        bmi < 27.0 -> ivResult.setImageResource(R.drawable.men_fat2)
                        else -> ivResult.setImageResource(R.drawable.men_fat1)
                    }
                }
                if (rbFemale.isChecked) {
                    when {
                        bmi < 18.5 -> ivResult.setImageResource(R.drawable.woman_fat3)
                        bmi < 24.0 -> ivResult.setImageResource(R.drawable.woman_fat4)
                        bmi < 27.0 -> ivResult.setImageResource(R.drawable.woman_fat2)
                        else -> ivResult.setImageResource(R.drawable.woman_fat1)
                    }
                }
            }else{
                Toast.makeText(this, "請輸入所有欄位", Toast.LENGTH_SHORT).show()
            }
        }





    }
}