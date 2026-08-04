package com.example.intent_11

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    private val registerCode = 200
    private lateinit var editTextPassword: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextName: EditText
    private lateinit var btnOk: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextName = findViewById(R.id.editText_registerName)
        editTextEmail = findViewById(R.id.editText_registerEmail)
        editTextPassword = findViewById(R.id.editText_registerPassword)
        btnCancel = findViewById(R.id.button_cancel)
        btnOk = findViewById(R.id.button_ok)
        
        btnCancel.setOnClickListener {
            editTextName.setText("")
            editTextEmail.setText("")
            editTextPassword.setText("")
        }
        
        btnOk.setOnClickListener {
            if (editTextName.text.isNotEmpty() && editTextEmail.text.isNotEmpty() && editTextPassword.text.isNotEmpty()) {

                val name = editTextName.text.toString()
                val email = editTextEmail.text.toString()
                val password = editTextPassword.text.toString()
                
                val resultIntent = Intent()
                resultIntent.putExtra("name", name)
                resultIntent.putExtra("email", email)
                resultIntent.putExtra("password", password)
                setResult(registerCode, resultIntent)
                finish()
                
            }else{
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
            
        }


    }
}
