package com.example.firebase02

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val edId = findViewById<EditText>(R.id.editText_id)
        val edName = findViewById<EditText>(R.id.editText_name)
        val edEmail = findViewById<EditText>(R.id.editText_email)
        val edPhone = findViewById<EditText>(R.id.editText_phone)
        
        val btnSet = findViewById<Button>(R.id.button_setData)
        val btnRead = findViewById<Button>(R.id.button_readData)
        val btnReset = findViewById<Button>(R.id.button_reset)
        
        val tvData = findViewById<TextView>(R.id.textView_data)

        btnSet.setOnClickListener {
            val id = edId.text.toString()
            val name = edName.text.toString()
            val email = edEmail.text.toString()
            val phone = edPhone.text.toString()

            val message = "Setting Data:\nID: $id\nName: $name\nEmail: $email\nPhone: $phone"
            tvData.text = message
            Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
        }

        btnRead.setOnClickListener {
            tvData.text = "Reading Data from Database..."
            // Add Firebase read logic here
        }

        btnReset.setOnClickListener {
            edId.text.clear()
            edName.text.clear()
            edEmail.text.clear()
            edPhone.text.clear()
            tvData.text = "TextView"
            Toast.makeText(this, "Fields cleared", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
