package com.example.listview8

import android.content.ContentValues
import android.content.DialogInterface
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DataActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var db: SQLiteDatabase
    private lateinit var listView: ListView
    private lateinit var tvCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_data)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        title = "Customer data"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvCount = findViewById(R.id.textView_dataCount)
        listView = findViewById(R.id.listView)
        dbHelper = DBHelper(this)
        db = dbHelper.readableDatabase

        displayData()
    }

    private fun displayData() {
        val searchName = intent.getStringExtra("SEARCH_NAME")
        Log.d("DataActivity", "Search name: '$searchName'")

        // 根據是否有搜尋姓名來決定查詢條件，改用 rawQuery 以確保與老師版邏輯一致
        val cursor: Cursor = if (!searchName.isNullOrEmpty()) {
            val query = "SELECT _id, name, email, phone, created_time FROM ${DBHelper.TABLE_NAME} WHERE name=?"
            db.rawQuery(query, arrayOf(searchName))
        } else {
            val query = "SELECT _id, name, email, phone, created_time FROM ${DBHelper.TABLE_NAME}"
            db.rawQuery(query, null)
        }

        val count = cursor.count
        Log.d("DataActivity", "Found records: $count")
        tvCount.text = "Select data , count = $count\n\n"

        // 設定資料庫欄位與 item_layout.xml 中元件的對應關係
        val from = arrayOf("_id", "name", "email", "phone", "created_time")
        val to = intArrayOf(
            R.id.textView_itemId,
            R.id.textView_itemName,
            R.id.textView_itemEmail,
            R.id.textView_itemPhone,
            R.id.textView_itemTime
        )

        // 建立 SimpleCursorAdapter
        val adapter = SimpleCursorAdapter(
            this,
            R.layout.item_layout,
            cursor,
            from,
            to,
            0
        )

        listView.adapter = adapter

        // 設定 ListView 的點擊監聽器，點擊後彈出更新對話框
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val cursor = parent.getItemAtPosition(position) as Cursor
            val recordId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Update data , id = $recordId")
            
            // 載入自定義的對話框佈局
            val dialogView = layoutInflater.inflate(R.layout.dialog_layout, null)
            val etName = dialogView.findViewById<EditText>(R.id.editText_dialogName)
            val etEmail = dialogView.findViewById<EditText>(R.id.editText_dialogEmail)
            val etPhone = dialogView.findViewById<EditText>(R.id.editText_dialogPhone)

            // 預填原始資料
            etName.setText(name)
            etEmail.setText(email)
            etPhone.setText(phone)

            builder.setView(dialogView)

            var newName: String
            var newEmail: String
            var newPhone: String

            builder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    if (etName.length() == 0) {
                        newName = name
                    } else {
                        newName = etName.text.toString()
                    }

                    if (etEmail.length() == 0) {
                        newEmail = email
                    } else {
                        newEmail = etEmail.text.toString()
                    }

                    if (etPhone.length() == 0) {
                        newPhone = phone
                    } else {
                        newPhone = etPhone.text.toString()
                    }

                    val cv = ContentValues()
                    cv.put("name", newName)
                    cv.put("email", newEmail)
                    cv.put("phone", newPhone)
                    
                    val count = db.update(DBHelper.TABLE_NAME, cv, "_id=?", arrayOf(recordId.toString()))
                    if (count != 0) {
                        // 有加localtime才會對
                        val cmd = "UPDATE ${DBHelper.TABLE_NAME} SET created_time=datetime('now', 'localtime') WHERE _id=$recordId"
                        db.execSQL(cmd)

                        // 同步圖片寫法：手動刷新 Cursor 與 Adapter
                        val query = "SELECT _id, name, email, phone, created_time FROM ${DBHelper.TABLE_NAME}"
                        val newDataCursor = db.rawQuery(query, null)
                        adapter.changeCursor(newDataCursor)
                        adapter.notifyDataSetChanged()
                    }

                    Toast.makeText(this@DataActivity, "Updated successfully", Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                }
            })

            builder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.dismiss()
                }
            })

            builder.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}
