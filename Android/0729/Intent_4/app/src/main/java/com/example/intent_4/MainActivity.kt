package com.example.intent_4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val Act1Code: Int = 100
    private val ACT2_Code: Int = 200

    private lateinit var textViewData: TextView
    private lateinit var buttonAct1: Button
    private lateinit var buttonAct2: Button
    private lateinit var editTextInput1: EditText
    private lateinit var editTextInput2: EditText

    private lateinit var getLaunch: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextInput1 = findViewById(R.id.editTextText_mainInput1)
        editTextInput2 = findViewById(R.id.editTextText_mainInput2)
        buttonAct1 = findViewById(R.id.button_mainToAct1)
        buttonAct2 = findViewById(R.id.button_mainToAct2)
        textViewData = findViewById(R.id.textView_mainData)
        textViewData.text = ""

        getLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            object : ActivityResultCallback<ActivityResult> {
                override fun onActivityResult(result: ActivityResult) {
                    Log.d("main", "get callback")
                    val code = result.resultCode
                    Log.d("main", "code = $code")
                    
                    if (code == Act1Code) {
                        val act1Intent = result.data
                        val data = act1Intent?.getStringExtra("act1_data")
                        textViewData.text = data
                    }
                    if (code == ACT2_Code) {
                        val act2Intent = result.data
                        val data = act2Intent?.getStringExtra("act2_data")
                        textViewData.text = data
                    }
                }
            }
        )

        buttonAct1.setOnClickListener {
            val data = if (editTextInput1.text.isEmpty()) "no data" else editTextInput1.text.toString()
            val intent = Intent(this@MainActivity, Activity_1::class.java)
            intent.putExtra("data", data)
            getLaunch.launch(intent)
        }

        buttonAct2.setOnClickListener {
            val data = if (editTextInput2.text.isEmpty()) "no data" else editTextInput2.text.toString()
            val intent = Intent(this@MainActivity, Activity_2::class.java)
            intent.putExtra("data", data)
            getLaunch.launch(intent)
        }
    }
}
