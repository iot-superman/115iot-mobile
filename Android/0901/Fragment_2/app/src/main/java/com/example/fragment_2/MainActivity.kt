package com.example.fragment_2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), FragmentDataListener {

    private lateinit var tabLayout: TabLayout
    private lateinit var buttonFrag3: Button
    private lateinit var buttonFrag2: Button
    private lateinit var buttonFrag1: Button
    private lateinit var editTextInput: EditText
    private lateinit var textViewReturn: TextView
    
    private lateinit var frag1: Fragment_1
    private lateinit var frag2: Fragment_2
    private lateinit var frag3: Fragment_3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tabLayout = findViewById(R.id.tabLayout)
        buttonFrag1 = findViewById(R.id.button_mainFrag1)
        buttonFrag2 = findViewById(R.id.button_mainFrag2)
        buttonFrag3 = findViewById(R.id.button_mainFrag3)
        editTextInput = findViewById(R.id.editText_mainInput)
        textViewReturn = findViewById(R.id.textView_returnM)

        frag1 = Fragment_1.newInstance("Fragment 1", "no data")
        frag2 = Fragment_2.newInstance("Fragment 2", "It is sunny day")
        frag3 = Fragment_3.newInstance("Fragment 3", "no data")

        val fragTransit = supportFragmentManager.beginTransaction()
        fragTransit.add(R.id.frameLayout_id, frag1, "frag-1")
        fragTransit.addToBackStack(null)
        fragTransit.commit()

        buttonFrag1.setOnClickListener(MyButton())
        buttonFrag2.setOnClickListener(MyButton())
        buttonFrag3.setOnClickListener(MyButton())
        
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragTransit = supportFragmentManager.beginTransaction()
                fragTransit.setCustomAnimations(R.anim.trans_in_from_left, R.anim.exit_to_alpha)
                when (tab?.position) {
                    0 -> fragTransit.replace(R.id.frameLayout_id, frag1, "frag-1")
                    1 -> fragTransit.replace(R.id.frameLayout_id, frag2, "frag-2")
                    2 -> fragTransit.replace(R.id.frameLayout_id, frag3, "frag-3")
                }
                fragTransit.commit()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onDataReceived(data: String) {
        textViewReturn.text = data
    }

    inner class MyButton : View.OnClickListener {
        override fun onClick(v: View?) {
            val fragTransit = supportFragmentManager.beginTransaction()
            fragTransit.setCustomAnimations(R.anim.trans_in_from_left, R.anim.exit_to_alpha)
            when (v?.id) {
                R.id.button_mainFrag1 -> fragTransit.replace(R.id.frameLayout_id, frag1, "frag-1")
                R.id.button_mainFrag2 -> fragTransit.replace(R.id.frameLayout_id, frag2, "frag-2")
                R.id.button_mainFrag3 -> fragTransit.replace(R.id.frameLayout_id, frag3, "frag-3")
            }
            fragTransit.commit()
        }
    }
}

interface FragmentDataListener {
    fun onDataReceived(data: String)
}