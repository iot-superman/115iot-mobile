package com.example.hw1_ex3

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var ed_other: EditText
    private lateinit var tv_result: TextView
    private lateinit var btn_ok: Button
    private lateinit var btn_cencel: Button
    private lateinit var cb_other: CheckBox
    private lateinit var cb_book: CheckBox
    private lateinit var cb_movie: CheckBox
    private lateinit var cb_ball: CheckBox
    private lateinit var rb_female: RadioButton
    private lateinit var rb_male: RadioButton
    private lateinit var rg_gender: RadioGroup
    private lateinit var ed_name: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ed_name = findViewById<EditText>(R.id.et_name)
        rg_gender = findViewById<RadioGroup>(R.id.rg_gender)
        rb_male = findViewById<RadioButton>(R.id.rb_male)
        rb_female = findViewById<RadioButton>(R.id.rb_female)
        cb_ball = findViewById<CheckBox>(R.id.cb_ball)
        cb_movie = findViewById<CheckBox>(R.id.cb_movie)
        cb_book = findViewById<CheckBox>(R.id.cb_book)
        cb_other = findViewById<CheckBox>(R.id.cb_other)
        ed_other = findViewById<EditText>(R.id.et_other)
        btn_cencel = findViewById<Button>(R.id.btn_cancel)
        btn_ok = findViewById<Button>(R.id.btn_ok)
        tv_result = findViewById<TextView>(R.id.tv_result)
        tv_result.text = ""

        btn_cencel.setOnClickListener {
            ed_name.setText("")
            rg_gender.clearCheck()
            cb_ball.isChecked = false
            cb_movie.isChecked = false
            cb_book.isChecked = false
            cb_other.isChecked = false
            tv_result.text = ""
        }

        btn_ok.setOnClickListener {
            tv_result.text = "姓名: ${ed_name.text}\n"
            if (rb_male.isChecked) {
                tv_result.append("性別: 男\n")
            } else if (rb_female.isChecked) {
                tv_result.append("性別: 女\n")
            }
            tv_result.append("興趣: ")
            if (cb_ball.isChecked) {
                tv_result.append("打球 ")
            }
            if (cb_movie.isChecked) {
                tv_result.append("看電影 ")
            }
            if (cb_book.isChecked) {
                tv_result.append("讀書 ")
            }
            if (cb_other.isChecked) {
                tv_result.append("${ed_other.text} \n")
            }
            tv_result.append("\n")


        }







    }
}