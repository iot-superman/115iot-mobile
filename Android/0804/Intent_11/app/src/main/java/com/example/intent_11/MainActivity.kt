package com.example.intent_11

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {
    private val registerCode = 200
    private val orderCode = 100
    private lateinit var txt_password: TextInputEditText
    private lateinit var txt_email: TextInputEditText
    private lateinit var txt_name: TextInputEditText
    private lateinit var btn_Register: Button
    private lateinit var btn_Order: Button
    private lateinit var tv_result: TextView

    // 使用 Lambda 語法定義結果接收器
    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        Log.d("main", "code = ${result.resultCode}")

        if (result.resultCode == registerCode) {
            result.data?.run {
                val name = getStringExtra("name")
                val email = getStringExtra("email")
                val password = getStringExtra("password")

                Log.d("main", "name = $name")
                Log.d("main", "email = $email")
                Log.d("main", "password = $password")

                tv_result.text = "Registered successfully!"
                txt_name.setText(name)
                txt_email.setText(email)
                txt_password.setText(password)

                val sp = getSharedPreferences("userdata", MODE_PRIVATE)
                sp.edit().apply {
                    putString("name", name)
                    putString("email", email)
                    putString("password", password)
                    apply()
                }
            }
        }else if (result.resultCode == orderCode) {
            val data = result.data?.getStringExtra("order")
         tv_result.text = data

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 初始化所有元件
        txt_name = findViewById(R.id.textInput_Name)
        txt_email = findViewById(R.id.textinput_Email)
        txt_password = findViewById(R.id.textInput_password)
        btn_Register = findViewById(R.id.button_Register)
        btn_Order = findViewById(R.id.button_Order)
        tv_result = findViewById(R.id.textView_result)

        btn_Register.setOnClickListener {
            val registerIntent = Intent(this@MainActivity, RegisterActivity::class.java)
            getResult.launch(registerIntent)
        }

        btn_Order.setOnClickListener {
            val inputName = txt_name.text.toString()
            val inputPassword = txt_password.text.toString()

            if (inputName.isNotEmpty() && inputPassword.isNotEmpty()) {
                val sp = getSharedPreferences("userdata", MODE_PRIVATE)
                val spName = sp.getString("name", "none")
                val spPassword = sp.getString("password", "none")

                Log.d("main", "spName=$spName")
                Log.d("main", "spPassword=$spPassword")

                if (inputName == spName && inputPassword == spPassword) {
                    val orderIntent = Intent(this@MainActivity, OrderActivity::class.java)
                    getResult.launch(orderIntent)
                } else {
                    Toast.makeText(this@MainActivity, "The name & password are wrong", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@MainActivity, "Please input name & password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
