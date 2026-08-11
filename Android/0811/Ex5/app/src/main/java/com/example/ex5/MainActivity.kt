package com.example.ex5

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerCity: Spinner
    private lateinit var npMonth: NumberPicker
    private lateinit var npDay: NumberPicker
    private lateinit var btnOk: Button
    private lateinit var tvResult: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set Title as per requirement
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "EX-5"
        toolbar.overflowIcon = ContextCompat.getDrawable(this, R.drawable.ic_menu_white)

        // Initialize UI elements
        spinnerCity = findViewById(R.id.spinnerCity)
        npMonth = findViewById(R.id.npMonth)
        npDay = findViewById(R.id.npDay)
        btnOk = findViewById(R.id.btnOk)
        tvResult = findViewById(R.id.tvResult)

        // Setup NumberPickers
        npMonth.minValue = 1
        npMonth.maxValue = 12
        npDay.minValue = 1
        npDay.maxValue = 31

        sharedPreferences = getSharedPreferences("CustomerData", Context.MODE_PRIVATE)

        btnOk.setOnClickListener {
            val city = spinnerCity.selectedItem.toString()
            val month = npMonth.value
            val day = npDay.value
            tvResult.text = "目前選擇：\n城市：$city\n生日：$month 月 $day 日"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                saveData()
                true
            }
            R.id.action_get -> {
                getData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveData() {
        val city = spinnerCity.selectedItemPosition
        val month = npMonth.value
        val day = npDay.value

        val editor = sharedPreferences.edit()
        editor.putInt("city_pos", city)
        editor.putInt("month", month)
        editor.putInt("day", day)
        editor.apply()

        tvResult.text = "資料已儲存到 SharedPreferences"
    }

    private fun getData() {
        val cityPos = sharedPreferences.getInt("city_pos", 0)
        val month = sharedPreferences.getInt("month", 1)
        val day = sharedPreferences.getInt("day", 1)

        spinnerCity.setSelection(cityPos)
        npMonth.value = month
        npDay.value = day

        val cityStr = spinnerCity.adapter.getItem(cityPos).toString()
        tvResult.text = "從 SharedPreferences 讀取資料：\n城市：$cityStr\n生日：$month 月 $day 日"
    }
}