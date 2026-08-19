package com.example.listview8

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * 主畫面活動，提供基本的資料新增、查詢、更新、刪除與重設操作介面。
 */
class MainActivity : AppCompatActivity() {
    private lateinit var db: SQLiteDatabase
    private lateinit var tvResult: TextView
    private lateinit var lvResult: ListView
    private lateinit var btnSelect: Button
    private lateinit var btnReset: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnInsert: Button
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var etName: EditText
    private lateinit var dbHelper: DBHelper

    /**
     * 初始化畫面元件、設定視窗邊距，並綁定資料庫與各按鈕的事件處理邏輯。
     *
     * @param savedInstanceState Activity 先前儲存的狀態資料；若為首次建立則可能為 `null`。
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 建立資料庫輔助物件，並取得可寫入的資料庫實例。
          dbHelper = DBHelper(this@MainActivity)
          db = dbHelper.writableDatabase

        // 綁定畫面上的輸入欄位、按鈕與結果顯示元件。
          etName = findViewById<EditText>(R.id.etName)
          etEmail = findViewById<EditText>(R.id.etEmail)
          etPhone = findViewById<EditText>(R.id.etPhone)
          btnInsert = findViewById<Button>(R.id.btnInsert)
          btnDelete = findViewById<Button>(R.id.btnDelete)
          btnUpdate = findViewById<Button>(R.id.btnUpdate)
          btnReset = findViewById<Button>(R.id.btnReset)
          btnSelect = findViewById<Button>(R.id.btnSelect)
          tvResult = findViewById<TextView>(R.id.tvResult)
          lvResult = findViewById<ListView>(R.id.lvResult)

        // 重設輸入欄位與查詢結果顯示內容。
        btnReset.setOnClickListener {
            etName.text.clear()
            etEmail.text.clear()
            etPhone.text.clear()
            tvResult.text = ""
        }
        btnInsert.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
                val cv = ContentValues()
                cv.put("name", name)
                cv.put("email", email)
                cv.put("phone", phone)
                
                val result = db.insert(DBHelper.TABLE_NAME, null, cv)
                if (result != -1L) {
                    Toast.makeText(this, "Success: Row ID $result", Toast.LENGTH_SHORT).show()
                    etName.text.clear()
                    etEmail.text.clear()
                    etPhone.text.clear()
                    btnSelect.performClick() // 自動重新整理下方列表
                } else {
                    Toast.makeText(this, "Insert Failed", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
        // 查詢資料表中的資料，若輸入姓名則過濾，否則顯示全部。
        btnSelect.setOnClickListener {
            val name = etName.text.toString().trim()
            
            val cursor = if (name.isNotEmpty()) {
                // 有輸入姓名，進行過濾
                db.query(
                    DBHelper.TABLE_NAME,
                    null,
                    "name=?",
                    arrayOf(name),
                    null,
                    null,
                    null
                )
            } else {
                // 姓名為空，查詢全部
                db.query(
                    DBHelper.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                )
            }
            if (cursor.count > 0) {
                val adapter = SimpleCursorAdapter(
                    this,
                    R.layout.item_layout,
                    cursor,
                    arrayOf("_id", "name", "email", "phone", "created_time"),
                    intArrayOf(
                        R.id.textView_itemId,
                        R.id.textView_itemName,
                        R.id.textView_itemEmail,
                        R.id.textView_itemPhone,
                        R.id.textView_itemTime
                    ),
                    0
                )
                lvResult.adapter = adapter
            } else {
                lvResult.adapter = null
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            }
        }

        // 依據姓名刪除對應資料，刪除成功後顯示提示訊息。
        btnDelete.setOnClickListener {
            val name = etName.text.toString().trim()
            if (name.isNotEmpty()) {
                val deletedRows = db.delete(DBHelper.TABLE_NAME, "name=?", arrayOf(name))
                if (deletedRows > 0) {
                    Toast.makeText(this, "Deleted $name", Toast.LENGTH_SHORT).show()
                    etName.text.clear() // 刪除成功後清空輸入框
                    btnSelect.performClick() // 自動重新整理下方列表
                } else {
                    Toast.makeText(this, "No such user: $name", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a name to delete", Toast.LENGTH_SHORT).show()
            }
        }

        // 依據姓名更新電子郵件與電話欄位，更新成功後顯示提示訊息。
        btnUpdate.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            if (name.isNotEmpty()) {
                val cv = ContentValues()
                cv.put("email", email)
                cv.put("phone", phone)
                val rows = db.update(DBHelper.TABLE_NAME, cv, "name=?", arrayOf(name))
                if (rows > 0) {
                    Toast.makeText(this, "Updated $name", Toast.LENGTH_SHORT).show()
                    btnSelect.performClick() // 自動重新整理下方列表
                } else {
                    Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a name to update", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_select_all -> {
                val intent = Intent(this, DataActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}