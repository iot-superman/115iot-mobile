package com.example.menu6_webview

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WebActivity : AppCompatActivity() {
    private var ipAddr = ""
    private lateinit var editTextIpAddr: EditText
    private lateinit var buttonLink: Button
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_web)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        title = "Web view"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editTextIpAddr = findViewById(R.id.editText_ipAddr)
        buttonLink = findViewById(R.id.button_link)
        webView = findViewById(R.id.webView_id)

        webView.settings.apply {
            javaScriptEnabled = true
            setSupportZoom(true)
            builtInZoomControls = true
        }

        buttonLink.setOnClickListener {
            if (editTextIpAddr.length() == 0) {
                ipAddr = "https://tw.yahoo.com/"
            } else {
                ipAddr = editTextIpAddr.text.toString()
            }

            // 正確寫法：直接賦值。
            // 不要用 .run { WebViewClient() }，那會造成 Cannot infer type 錯誤且無效。
            webView.webViewClient = WebViewClient()
            webView.loadUrl(ipAddr)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}