package com.example.intent_6

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var imgButtonEmail: ImageButton
    private lateinit var imgButtonPhone: ImageButton
    private lateinit var editTextData: EditText
    private lateinit var imgButtonHttp: ImageButton
    private lateinit var imgButtonSearch: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextData = findViewById(R.id.editTextText)
        imgButtonPhone = findViewById(R.id.imageButton_phone)
        imgButtonEmail = findViewById(R.id.imageButton__email)
        imgButtonHttp = findViewById(R.id.imageButton_http)
        imgButtonSearch = findViewById(R.id.imageButton_search)

        imgButtonPhone.setOnClickListener {
//            //Teacher Version:
//            if (editTextData.length()!=0){
//                val tel = editTextData.text.toString()
//                val uri = Uri.parse(tel)
//                val it = Intent(Intent.ACTION_DIAL, uri)
//                startActivity(it)
//            }

            if (editTextData.text.isNotEmpty()) {
                val uri = ("tel:" + editTextData.text.toString()).toUri()
                val it = Intent(Intent.ACTION_DIAL, uri)
                startActivity(it)
            }
        }

        imgButtonEmail.setOnClickListener {
            if (editTextData.text.isNotEmpty()) {
                val email = editTextData.text.toString()
                val uri = Uri.parse("mailto:$email")
                Log.d("main", "email uri= $uri")
                val action = Intent.ACTION_SENDTO
                val intent = Intent(action, uri)
                startActivity(intent)
            }
        }

        imgButtonHttp.setOnClickListener {
            if (editTextData.text.isNotEmpty()) {
                var url = editTextData.text.toString()
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://$url"
                }
                val uri = url.toUri()
                val it = Intent(Intent.ACTION_VIEW, uri)
                startActivity(it)
            }
        }

        imgButtonSearch.setOnClickListener {
            val data = if (editTextData.length()==0) "python" else editTextData.text.toString()
                val action = Intent.ACTION_WEB_SEARCH
                val intent = Intent(action)
                intent.putExtra(SearchManager.QUERY,data)
                startActivity(intent)

        }
    }
}
