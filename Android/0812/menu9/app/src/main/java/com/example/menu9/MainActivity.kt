package com.example.menu9

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var textViewData1: TextView
    private lateinit var textViewData2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 初始化元件
        textViewData1 = findViewById(R.id.textView_data1)
        textViewData2 = findViewById(R.id.textView_data2)
        textViewData1.text = ""
        textViewData2.text = ""

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomAppBar_id)
        val floatingButton: FloatingActionButton = findViewById(R.id.floatingActionButton_id)

        // 徹底禁用圖示著色與指示器 (確保圖片顯示原色)
        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.isItemActiveIndicatorEnabled = false
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_cat1 -> {
                    textViewData1.text = "Cat 1"
                }
                R.id.menu_cat2 -> {
                    textViewData1.text = "Cat 2"
                }
                R.id.menu_cat3 -> {
                    textViewData1.text = "Cat 3"
                }
                R.id.menu_dog1 -> {
                    textViewData2.text = "Dog 1"
                }
                R.id.menu_dog2 -> {
                    textViewData2.text = "Dog 2"
                }
                R.id.menu_about -> {
                    textViewData2.text = "About bottom menu"
                }
            }
            true
        }

        // 設定 FAB 點擊事件
        floatingButton.setOnClickListener {
            Toast.makeText(this@MainActivity, "Floating button is pressed", Toast.LENGTH_SHORT).show()
        }
    }
}