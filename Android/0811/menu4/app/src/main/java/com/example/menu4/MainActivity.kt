package com.example.menu4

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var imageViewPic: ImageView
    private lateinit var textViewData: TextView
    private lateinit var layoutId: ConstraintLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageViewPic = findViewById<ImageView>(R.id.imageView_pic)
        textViewData = findViewById<TextView>(R.id.textView)
        layoutId = findViewById<ConstraintLayout>(R.id.layoutId)

        registerForContextMenu(layoutId)
        registerForContextMenu(imageViewPic)

        // 設定點擊圖片就直接開啟選單
        imageViewPic.setOnClickListener {
            openContextMenu(it)
        }
        
        // 設定點擊背景也直接開啟選單
        layoutId.setOnClickListener {
            openContextMenu(it)
        }
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // 確保每次開啟選單前都是乾淨的，避免多個選單疊加
        menu?.clear()

        when (v?.id) {
            R.id.imageView_pic -> {
                menuInflater.inflate(R.menu.image_menu, menu)
                menu?.setHeaderTitle("選擇花朵")
            }
            R.id.layoutId -> {
                menuInflater.inflate(R.menu.layout_menu, menu)
                menu?.setHeaderTitle("選擇背景顏色")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_f1 -> {
                textViewData.text = "This is flower 1"
                imageViewPic.setImageResource(R.drawable.flower_1)
                true
            }
            R.id.menu_f2 -> {
                textViewData.text = "This is flower 2"
                imageViewPic.setImageResource(R.drawable.flower_2)
                true
            }
            R.id.menu_f3 -> {
                textViewData.text = "This is flower 3"
                imageViewPic.setImageResource(R.drawable.flower_3)
                true
            }
            R.id.menu_blue -> {
                layoutId.setBackgroundColor(android.graphics.Color.BLUE)
                true
            }
            R.id.menu_green -> {
                layoutId.setBackgroundColor(android.graphics.Color.GREEN)
                true
            }
            R.id.menu_reset -> {
                layoutId.setBackgroundColor(android.graphics.Color.WHITE)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
