package com.example.widget_8

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var btnCancel: Button
    private lateinit var tvBmi: TextView
    private lateinit var btnCalculate: Button
    private lateinit var etWeight: TextInputEditText
    private lateinit var etHeight: TextInputEditText
    private lateinit var etName: TextInputEditText

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

        etName = findViewById(R.id.textInput_Name)
        etHeight = findViewById(R.id.textInput_Height)
        etWeight = findViewById(R.id.textInput_Weight)

        btnCancel = findViewById(R.id.button_Cancel)
        btnCalculate = findViewById(R.id.button_BMI)
        tvBmi = findViewById(R.id.textView_BMI)
        tvBmi.text = ""

        tvBmi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)


        btnCancel.setOnClickListener {
            etName.text?.clear()
            etHeight.text?.clear()
            etWeight.text?.clear()
            tvBmi.text = ""
            tvBmi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        }
        btnCalculate.setOnClickListener {
            val name = etName.text.toString()
            val heightStr = etHeight.text.toString()
            val weightStr = etWeight.text.toString()

            when {
                name.isEmpty() -> Toast.makeText(this, getString(R.string.error_name), Toast.LENGTH_SHORT).show()
                heightStr.isEmpty() -> Toast.makeText(this, getString(R.string.error_height), Toast.LENGTH_SHORT).show()
                weightStr.isEmpty() -> Toast.makeText(this, getString(R.string.error_weight), Toast.LENGTH_SHORT).show()
                else -> {
                    val height = heightStr.toDoubleOrNull() ?: 0.0
                    if (height > 0) {
                        val bmi = calculateBMI()
                        updateBmiDisplay(bmi, name, heightStr, weightStr)
                    } else {
                        Toast.makeText(this, getString(R.string.error_height), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    fun calculateBMI(): Double {
        val weight = etWeight.text.toString().toDoubleOrNull() ?: 0.0
        val height = etHeight.text.toString().toDoubleOrNull() ?: 0.0
        if (height == 0.0) return 0.0
        val heightInMeters = height / 100
        return weight / (heightInMeters * heightInMeters)
    }
    private fun updateBmiDisplay(bmi: Double, name: String, height: String, weight: String) {
        val status: String
        val drawableRes: Int

        when {
            bmi < 18.5 -> {
                status = getString(R.string.bmi_underweight)
                drawableRes = R.drawable.fat_1
            }
            bmi < 24.0 -> {
                status = getString(R.string.bmi_normal)
                drawableRes = R.drawable.fat_4
            }
            bmi < 27.0 -> {
                status = getString(R.string.bmi_overweight)
                drawableRes = R.drawable.fat_2
            }
            else -> {
                status = getString(R.string.bmi_obese)
                drawableRes = R.drawable.fat_1
            }
        }

        tvBmi.text = getString(R.string.bmi_result, name, height, weight, bmi, status)
        tvBmi.setCompoundDrawablesWithIntrinsicBounds(drawableRes, 0, 0, 0)
    }

}