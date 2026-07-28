package com.example.intent2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var floatButton: Button
    private lateinit var editTextWeight: EditText
    private lateinit var editTextHeight: EditText
    private lateinit var editTextName: EditText
    private lateinit var bmiButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editTextName = findViewById<EditText>(R.id.editText_name)
        editTextHeight = findViewById<EditText>(R.id.editText_height)
        editTextWeight = findViewById<EditText>(R.id.editText_weight)

        bmiButton = findViewById<Button>(R.id.button_bmi)
        bmiButton.setOnClickListener {
            if(editTextName.length()!=0 && editTextHeight.length()!=0 && editTextWeight.length()!=0) {
                val name = editTextName.text.toString()
                val height = editTextHeight.text.toString().toInt()
                val weight = editTextWeight.text.toString().toInt()
                /*
                1. 建立 Intent 物件
                Kotlin
                val intent = Intent(this, BMIActivity::class.java)
                •
                Intent: 是 Android 中各組件（如 Activity）之間溝通的橋樑。
                •
                this: 代表當前的頁面（MainActivity），也就是發送請求的地方。
                •
                BMIActivity::class.java: 指定目標頁面為 BMIActivity。這告訴系統：「我要從當前頁面跳轉到 BMI 計算結果頁面」。
                2. 放入資料 (putExtra)
                putExtra 是用來將資料打包進 Intent 的方法，採用 Key-Value（鍵值對） 的方式存儲：
                 */
                val intent = Intent(this, BMIActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("height", height)
                intent.putExtra("weight", weight)

                startActivity(intent)
            }else{
                Toast.makeText(this@MainActivity, "請完整輸入資料", Toast.LENGTH_SHORT).show()
            }
        }
        floatButton = findViewById<Button>(R.id.button_bmiFloat)
        floatButton.setOnClickListener {
            if(editTextName.length()!=0 && editTextHeight.length()!=0 && editTextWeight.length()!=0) {
                val name = editTextName.text.toString()
                val height = editTextHeight.text.toString().toFloat()
                val weight = editTextWeight.text.toString().toFloat()
                val intent = Intent(this, BmiFloatActivity::class.java)
                val bundle = Bundle()
                /* bundle
                Bundle 就像是一個「包裹」或「容器」，它可以裝載多種不同類型的資料。
                與直接使用 intent.putExtra 相比，使用 Bundle 的好處包括：
                1. 模組化：將所有要傳遞的資料先打包成一個物件，結構更清晰。
                2. 複用性：同一個 Bundle 包裹可以被傳遞給不同的 Intent 或 Fragment。
                3. 批次處理：使用 intent.putExtras(bundle) 一次性放入所有資料。
                 */
                bundle.putString("name", name)
                bundle.putFloat("height", height)
                bundle.putFloat("weight", weight)
                intent.putExtras(bundle)

                startActivity(intent)
            }else{
                Toast.makeText(this@MainActivity, "請完整輸入資料", Toast.LENGTH_SHORT).show()
            }
        }
    }






}