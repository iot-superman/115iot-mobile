package com.example.fragment_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private lateinit var buttonFrag3: Button
    private lateinit var buttonFrag2: Button
    private lateinit var buttonFrag1: Button
    private lateinit var editTextInput: EditText
    private lateinit var frag1: Fragment_1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonFrag1 = findViewById(R.id.button_mainFrag1)
        buttonFrag2 = findViewById(R.id.button_mainFrag2)
        buttonFrag3 = findViewById(R.id.button_mainFrag3)

        editTextInput = findViewById(R.id.editText_mainInput)

        frag1 = Fragment_1.newInstance("Fragment 1", "no data")
        val fragTransit = supportFragmentManager.beginTransaction()
        fragTransit.add(R.id.frameLayout_id, frag1, "frag-1")
        fragTransit.addToBackStack(null)
        fragTransit.commit()


    }


}