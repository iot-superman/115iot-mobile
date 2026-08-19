package com.example.listview8

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(myContext: Context) : SQLiteOpenHelper(myContext, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "customer.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "user_table"
        val CREATE_TABLE_SQL = """
            CREATE TABLE $TABLE_NAME (
                _id INTEGER PRIMARY KEY ,
                name TEXT,
                email TEXT,
                phone TEXT,
                created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """.trimIndent()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_SQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}