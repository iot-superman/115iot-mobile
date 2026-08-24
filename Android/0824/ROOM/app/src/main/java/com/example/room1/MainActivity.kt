package com.example.room1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var btnSelect: Button
    private lateinit var btnSelectOnem: Button
    private lateinit var btnReset: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnInsert: Button
    private lateinit var userDB: MyDatabase
    private lateinit var dao: DataDao

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var tvResult: TextView
    private lateinit var lvResult: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. 初始化 Room
        userDB = MyDatabase.getInstance(this)
        dao = userDB.dataDao()

        // 2. 綁定 UI
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        tvResult = findViewById(R.id.tvResult)
        lvResult = findViewById(R.id.lvResult)

         btnInsert= findViewById<Button>(R.id.btnInsert)
         btnDelete = findViewById<Button>(R.id.btnDelete)
         btnUpdate = findViewById<Button>(R.id.btnUpdate)
         btnReset = findViewById<Button>(R.id.btnReset)


         btnSelectOnem = findViewById<Button>(R.id.btnSelectOne)
         btnSelect = findViewById<Button>(R.id.btnSelect)

        // 3. 按鈕功能實作

        // 新增
        btnInsert.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "請輸入姓名", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val data = CustomerData(name = name, email = email, phone = phone)
                dao.insertCustomer(data)
                refreshList()
                clearInputs()
                Toast.makeText(this@MainActivity, "新增成功", Toast.LENGTH_SHORT).show()
            }
        }

        // 刪除 (以姓名作為基準)
        btnDelete.setOnClickListener {
            val name = etName.text.toString()
            if (name.isEmpty()) return@setOnClickListener

            lifecycleScope.launch {
                val list = dao.getCustomer(name)
                if (list.isNotEmpty()) {
                    dao.delete(list[0])
                    refreshList()
                    Toast.makeText(this@MainActivity, "刪除成功", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "找不到該姓名", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 更新 (以姓名作為查詢基準)
        btnUpdate.setOnClickListener {
            val name = etName.text.toString()
            if (name.isEmpty()) return@setOnClickListener

            lifecycleScope.launch {
                val list = dao.getCustomer(name)
                if (list.isNotEmpty()) {
                    val target = list[0]
                    val updatedData = target.copy(
                        email = etEmail.text.toString(),
                        phone = etPhone.text.toString()
                    )
                    dao.update(updatedData)
                    refreshList()
                    Toast.makeText(this@MainActivity, "更新成功", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "找不到該姓名", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 重置輸入框
        btnReset.setOnClickListener {
            clearInputs()
        }

        // 查詢單一筆 (依據姓名)
        btnSelectOnem.setOnClickListener {
            val name = etName.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(this, "請輸入姓名以查詢", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val list = dao.getCustomer(name)
                if (list.isNotEmpty()) {
                    val data = list[0]
                    // 將結果顯示在 EditText 方便修改，並更新 ListView 僅顯示該筆
                    etEmail.setText(data.email)
                    etPhone.setText(data.phone)
                    
                    val adapter = ArrayAdapter(
                        this@MainActivity,
                        android.R.layout.simple_list_item_1,
                        listOf("${data.uid} | ${data.name} | ${data.phone}")
                    )
                    lvResult.adapter = adapter
                    tvResult.text = "查詢結果: 1 筆"
                } else {
                    Toast.makeText(this@MainActivity, "查無此人", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 查詢全部
        btnSelect.setOnClickListener {
            refreshList()
        }

        // 進入畫面時先載入一次列表
        refreshList()
    }

    private fun refreshList() {
        lifecycleScope.launch {
            val allData = dao.getAll()
            val adapter = ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_list_item_1,
                allData.map { "${it.uid} | ${it.name} | ${it.phone}" }
            )
            lvResult.adapter = adapter
            tvResult.text = "資料筆數: ${allData.size}"
        }
    }

    private fun clearInputs() {
        etName.text.clear()
        etEmail.text.clear()
        etPhone.text.clear()
    }
}
