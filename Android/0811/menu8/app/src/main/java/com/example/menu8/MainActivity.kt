package com.example.menu8

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var now: Calendar
    private lateinit var datePicker: DatePicker
    private lateinit var textViewData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewData = findViewById<TextView>(R.id.textView)
        datePicker = findViewById<DatePicker>(R.id.datePicker_id)

        now = Calendar.getInstance()

        val year = now.get(Calendar.YEAR)
        val month = now.get(Calendar.MONTH)
        val day = now.get(Calendar.DAY_OF_MONTH)

        val selectPrefix = getString(R.string.select_text)
        textViewData.text = "$selectPrefix$year/${month + 1}/$day"

        datePicker.init(year, month, day) { _, year, monthOfYear, dayOfMonth ->
            textViewData.text = "$selectPrefix$year/${monthOfYear + 1}/$dayOfMonth"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_1, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_date -> {
                val dateDialog = DatePickerDialog(
                    this@MainActivity, myDate,
                    now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
                )
                dateDialog.setMessage("Please select date")
                dateDialog.show()
                true
            }
            R.id.menu_time -> {
                val intent = Intent(this@MainActivity, TimeActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    val myDate = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            now.set(Calendar.YEAR, year)
            now.set(Calendar.MONTH, month)
            now.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val selectPrefix = getString(R.string.select_text)
            textViewData.text = "$selectPrefix$year/${month + 1}/$dayOfMonth"
            datePicker.updateDate(year, month, dayOfMonth)
        }
    }
}
