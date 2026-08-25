package com.example.firebase01

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var dataRef: DatabaseReference
    private lateinit var fbControl: FirebaseDatabase

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
        Log.d("main", "fbControl = $fbControl")

        dataRef = fbControl.getReference().child("class_2")
        Log.d("main", "dataRef = $dataRef")

        dataRef.child("1").child("name").setValue("AAAA")
        dataRef.child("1").child("email").setValue("aaa@gmail.com")
        dataRef.child("1").child("phone").setValue("11111")

        dataRef.child("2").child("name").setValue("BBBB")
        dataRef.child("2").child("email").setValue("bbb@gmail.com")
        dataRef.child("2").child("phone").setValue("22222")

        dataRef.child("3").child("name").setValue("CCCC")
        dataRef.child("3").child("email").setValue("ccc@gmail.com")
        dataRef.child("3").child("phone").setValue("33333")

        dataRef.child("1").child("sex").setValue("Female")

        val data = mutableMapOf<String, String>()
        data["name"] = "Mary"
        data["email"]="mary@gmail.com"
        data["phone"]="44444"
        dataRef.child("4").setValue(data)

    }
}
