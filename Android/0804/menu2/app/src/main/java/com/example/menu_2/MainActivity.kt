package com.example.menu_2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var textViewData: TextView
    private lateinit var imageViewPic: ImageView
    private var picFlag = true
    private var colorFlag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 照老師版本的變數名稱與旗標初始化
        textViewData = findViewById(R.id.textView_data)
        imageViewPic = findViewById(R.id.imageView_pic)
        picFlag = true
        colorFlag = true

    }//end of onCreate()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.setup, menu)  //使用自己寫的設定檔
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.changePicture -> {
                if (picFlag) {
                    imageViewPic.setImageResource(R.drawable.flower2)
                    picFlag = false
                } else {
                    imageViewPic.setImageResource(R.drawable.flower1)
                    picFlag = true
                }
                return true
            }
            R.id.changeColor -> {
                if (colorFlag) {
                    textViewData.setTextColor(Color.RED)
                    colorFlag = false
                } else {
                    textViewData.setTextColor(Color.BLACK)
                    colorFlag = true
                }
                return true
            }
            R.id.text_1 -> {
                textViewData.text = "This is flower 1"
                return true
            }
            R.id.text_2 -> {
                textViewData.text = "This is flower 2"
                return true
            }
            R.id.about -> {
                Toast.makeText(this, "Menu App v1.0", Toast.LENGTH_SHORT).show()
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Menu App")
                builder.setIcon(R.drawable.flower2)
                builder.setMessage("Would you like to change to Display Activity?")
                builder.setPositiveButton("OK") { dialog, _ ->
                    val intent = Intent(this@MainActivity, DisplayActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
                builder.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}