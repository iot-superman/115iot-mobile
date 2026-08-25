package com.example.ex6

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvMeal: TextView
    private lateinit var tvTotal: TextView
    private lateinit var btnChoice: Button
    private lateinit var listView: ListView
    private lateinit var imageViewPreview: ImageView
    
    private lateinit var tvSelectedName: TextView
    private lateinit var tvSelectedPrice: TextView
    
    private var selectedDrinkName: String = ""
    private var selectedDrinkPrice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvName = findViewById(R.id.tv_name)
        tvMeal = findViewById(R.id.tv_meal)
        tvTotal = findViewById(R.id.tv_total)
        btnChoice = findViewById(R.id.btn_choice)
        listView = findViewById(R.id.listView)
        imageViewPreview = findViewById(R.id.imageView_preview)
        
        tvSelectedName = findViewById(R.id.textView_name)
        tvSelectedPrice = findViewById(R.id.textView_Price)

        // 從 strings.xml 讀取資料
        val drinkNames = resources.getStringArray(R.array.drink_names)
        val drinkPrices = resources.getIntArray(R.array.drink_prices)
        val drinkImages = resources.obtainTypedArray(R.array.drink_images)

        // 預先取得圖片 Res ID 並回收 TypedArray
        val imageResIds = IntArray(drinkNames.size) { i -> drinkImages.getResourceId(i, -1) }
        drinkImages.recycle()

        // 設定 ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, drinkNames)
        listView.adapter = adapter

        // ListView 點擊事件
        listView.setOnItemClickListener { _, _, position, _ ->
            selectedDrinkName = drinkNames[position]
            selectedDrinkPrice = drinkPrices[position]
            
            tvSelectedName.text = "品名：$selectedDrinkName"
            tvSelectedPrice.text = "單價：NT$ $selectedDrinkPrice"
            
            if (imageResIds[position] != -1) {
                imageViewPreview.setImageResource(imageResIds[position])
            }
        }

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                intent?.extras?.let {
                    val drink = it.getString("drink")
                    val quantityStr = it.getString("quantity") ?: "0"
                    val sugar = it.getString("sugar")
                    val ice = it.getString("ice")
                    
                    val quantity = quantityStr.toIntOrNull() ?: 0
                    val total = quantity * selectedDrinkPrice

                    tvName.text = "飲料名稱: $drink"
                    tvMeal.text = "數量: $quantity, 甜度: $sugar, 冰塊: $ice"
                    tvTotal.text = "總價: NT$ $total"
                }
            }
        }

        btnChoice.setOnClickListener {
            if (selectedDrinkName.isEmpty()) return@setOnClickListener
            
            val intent = Intent(this, SelectActivity::class.java)
            intent.putExtra("selected_drink", selectedDrinkName)
            launcher.launch(intent)
        }
    }
}
