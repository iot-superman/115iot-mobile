package com.example.widget_7

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var ratingBar: RatingBar
    private lateinit var buttonOk: Button
    private lateinit var buttonCancel: Button
    private lateinit var textViewArticle: TextView
    private lateinit var editTextContent: TextView
    private lateinit var editTextDate: TextView
    private lateinit var editTextName: TextView

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
        editTextName = findViewById<TextView>(R.id.editText_Name)
        editTextDate = findViewById<TextView>(R.id.editText_Date)
        editTextContent = findViewById<TextView>(R.id.editText_content)
        textViewArticle = findViewById<TextView>(R.id.textView_article)
        textViewArticle.text =""

        buttonCancel = findViewById<Button>(R.id.btn_Cencel)
        buttonOk = findViewById<Button>(R.id.btn_Ok)
        ratingBar = findViewById<RatingBar>(R.id.ratingBar_star)

        buttonCancel.setOnClickListener {
            editTextName.setText("")
            editTextDate.setText("")
            editTextContent.setText("")
            textViewArticle.text = ""
            ratingBar.rating = 3.0f
        }

        buttonOk.setOnClickListener {
            if (editTextName.length()==0 ||editTextDate.length()==0 ) {
                textViewArticle.text = ""
                Toast.makeText(this@MainActivity, "Please input name and date", Toast.LENGTH_SHORT).show()

            }else {
                var name = editTextName.text.toString()
                var date = editTextDate.text.toString()
                textViewArticle.text = "Name : $name\nDate : $date\n\n"
            }
            var data = editTextContent.text.toString()
            textViewArticle.append("Content : \n$data\n")
            textViewArticle.append("rating=${ratingBar.rating}")

            ratingBar.onRatingBarChangeListener = object : RatingBar.OnRatingBarChangeListener {
                override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean
                ) {
                   Log.d("Main","rating=$rating")
                }
            }




        }

    }
}