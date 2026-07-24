package com.example.ex2

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var name: TextInputEditText
    private lateinit var age: TextInputEditText
    private lateinit var rbgender: RadioGroup
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton
    private lateinit var height: TextInputEditText
    private lateinit var weight: TextInputEditText
    private lateinit var btnCancel: Button
    private lateinit var btnBMI: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        name = findViewById(R.id.etName)
        age = findViewById(R.id.etAge)
        rbgender = findViewById(R.id.rgGender)
        rbMale = findViewById(R.id.rbMale)
        rbFemale = findViewById(R.id.rbFemale)
        height = findViewById(R.id.etHeight)
        weight = findViewById(R.id.etWeight)
        btnCancel = findViewById(R.id.btnCancel)
        btnBMI = findViewById(R.id.btnBMI)
        tvResult = findViewById(R.id.tvResult)
    }

    private fun setupClickListeners() {
        btnCancel.setOnClickListener {
            name.text?.clear()
            age.text?.clear()
            rbgender.clearCheck()
            height.text?.clear()
            weight.text?.clear()
            tvResult.text = ""
            tvResult.setCompoundDrawables(null, null, null, null)
        }

        btnBMI.setOnClickListener {
            val nameStr = name.text.toString()
            val ageStr = age.text.toString()
            val hStr = height.text.toString()
            val wStr = weight.text.toString()

            if (nameStr.isEmpty() || ageStr.isEmpty() || hStr.isEmpty() || wStr.isEmpty()) {
                Toast.makeText(this, "請輸入所有欄位", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val h = hStr.toDoubleOrNull()
            val w = wStr.toDoubleOrNull()

            if (h == null || h <= 0 || w == null || w <= 0) {
                Toast.makeText(this, "身高或體重格式錯誤", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val bmi = calculateBMI(h, w)
            val bmiCategory = getBMICategory(bmi)
            val gender = when (rbgender.checkedRadioButtonId) {
                R.id.rbMale -> "男"
                R.id.rbFemale -> "女"
                else -> "未指定"
            }

            tvResult.text = "Name: $nameStr\nAge: $ageStr\nSex: $gender\nBMI: ${String.format("%.2f", bmi)}\nBMI 結果: $bmiCategory"
            updateResultDrawable(bmi)
        }
    }

    private fun calculateBMI(height: Double, weight: Double): Double {
        val heightInMeters = height / 100
        return weight / (heightInMeters * heightInMeters)
    }

    private fun getBMICategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "過輕"
            bmi < 24.0 -> "正常"
            bmi < 27.0 -> "過重"
            else -> "肥胖"
        }
    }

    private fun updateResultDrawable(bmi: Double) {
        val imageRes = if (rbMale.isChecked) {
            when {
                bmi < 18.5 -> R.drawable.men_fat3
                bmi < 24.0 -> R.drawable.men_fat4
                bmi < 27.0 -> R.drawable.men_fat2
                else -> R.drawable.men_fat1
            }
        } else if (rbFemale.isChecked) {
            when {
                bmi < 18.5 -> R.drawable.woman_fat3
                bmi < 24.0 -> R.drawable.woman_fat4
                bmi < 27.0 -> R.drawable.woman_fat2
                else -> R.drawable.woman_fat1
            }
        } else null

        if (imageRes != null) {
            val drawable: Drawable? = ContextCompat.getDrawable(this, imageRes)
            // 將圖片縮放到 120dp 以符合 "fit" 的要求
            val size = (120 * resources.displayMetrics.density).toInt()
            drawable?.setBounds(0, 0, size, size)
            tvResult.setCompoundDrawables(drawable, null, null, null)
        } else {
            tvResult.setCompoundDrawables(null, null, null, null)
        }
    }
}
