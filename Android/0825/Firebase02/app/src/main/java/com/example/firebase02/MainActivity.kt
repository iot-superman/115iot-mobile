package com.example.firebase02

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {


    private lateinit var firebaseList: MutableList<MutableMap<String, String>>
    private lateinit var fbControl: FirebaseDatabase
    private lateinit var dataRef: DatabaseReference
    private val DisplayAll = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fbControl = FirebaseDatabase.getInstance()
        dataRef = fbControl.getReference().child("customer")
//        val data = mutableMapOf<String, String>()
//        data["name"] = "AAAA"
//        data["email"] = "aaa@gmail.com"
//        data["phone"] = "12345"
        // dataRef.child("1").setValue(data)
        firebaseList = mutableListOf<MutableMap<String, String>>()
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("main", "onDataChange")
                firebaseList.clear()
                for (ds in snapshot.children) {
                    val name = ds.child("name").value.toString()
                    val email = ds.child("email").value.toString()
                    val phone = ds.child("phone").value.toString()
                    val id = ds.key.toString()
                    val data = mutableMapOf<String, String>()
                    data["id"] = id
                    data["name"] = name
                    data["email"] = email
                    data["phone"] = phone
//                    data.put("id", id)
//                    data.put("name", name)
//                    data.put("email", email)
//                    data.put("phone", phone)
                    firebaseList.add(data)
                }
                Log.d("main", "firebaseList = $firebaseList")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        val editTextId = findViewById<EditText>(R.id.editText_id)
        val editTextName = findViewById<EditText>(R.id.editText_name)
        val editTextEmail = findViewById<EditText>(R.id.editText_email)
        val editTextPhone = findViewById<EditText>(R.id.editText_phone)

        val buttonSetData = findViewById<Button>(R.id.button_setData)
        val buttonReadData = findViewById<Button>(R.id.button_readData)
        val buttonReset = findViewById<Button>(R.id.button_reset)

        val textViewData = findViewById<TextView>(R.id.textView_data)
        textViewData.text = ""

        buttonReset.setOnClickListener {
            editTextId.setText("")
            editTextName.setText("")
            editTextEmail.setText("")
            editTextPhone.setText("")
            textViewData.text = ""
        }

        buttonSetData.setOnClickListener {
            if (editTextId.length() != 0 && editTextName.length() != 0 && editTextEmail.length() != 0 && editTextPhone.length() != 0) {
                val id = editTextId.text.toString()
                val name = editTextName.text.toString()
                val email = editTextEmail.text.toString()
                val phone = editTextPhone.text.toString()

                val data = mutableMapOf<String, String>()
                data.put("name", name)
                data.put("email", email)
                data.put("phone", phone)
                dataRef.child(id).setValue(data)
                textViewData.text = "id = $id , Data set OK"
            }
        }

        buttonReadData.setOnClickListener {
            textViewData.text = "Reading Data from Database..."
            if (editTextId.length()!=0) {
                val inputId = editTextId.text.toString()
                for (data in firebaseList) {
                    if (data["id"] == inputId) {
                        textViewData.text = "id = ${data["id"]}\n"
                        textViewData.append("name = ${data["name"]}\n")
                        textViewData.append("email = ${data["email"]}\n")
                        textViewData.append("phone = ${data["phone"]}\n")
                        break
                    }
                    }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menu?.add(1, DisplayAll, Menu.NONE, "Display all")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == DisplayAll) {
            // Logic for Display all
        }
        return super.onOptionsItemSelected(item)
    }
}
