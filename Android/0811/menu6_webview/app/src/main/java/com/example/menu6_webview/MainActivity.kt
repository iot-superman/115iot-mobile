package com.example.menu6_webview

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonLogin = findViewById<Button>(R.id.button_login)
        buttonLogin.setOnClickListener {
            val myBuilder = Dialog(this@MainActivity)
            myBuilder.setContentView(R.layout.dialog_layout)
            val editTextName = myBuilder.findViewById<EditText>(R.id.editText_dialogName)
            val editTextEmail = myBuilder.findViewById<EditText>(R.id.editText_dialogEmail)
            val buttonCancel = myBuilder.findViewById<Button>(R.id.button_dialogCancel)
            val buttonOk = myBuilder.findViewById<Button>(R.id.button_dialogOk)
            myBuilder.show()

            buttonCancel.setOnClickListener {
                myBuilder.dismiss()
            }

            buttonOk.setOnClickListener {
                val name = editTextName.text.toString()
                val email = editTextEmail.text.toString()
                Toast.makeText(this@MainActivity, "name = $name , email = $email", Toast.LENGTH_SHORT).show()
                myBuilder.dismiss()
            }
        }
    }
}