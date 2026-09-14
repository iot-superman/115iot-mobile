package com.example.http_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var editTextIP: EditText
    private lateinit var textViewResponse: TextView
    private lateinit var switches: List<SwitchMaterial>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextIP = findViewById(R.id.editTextIP)
        textViewResponse = findViewById(R.id.textViewResponse)
        val buttonLink = findViewById<Button>(R.id.buttonLink)

        switches = listOf(
            findViewById(R.id.switch1),
            findViewById(R.id.switch2),
            findViewById(R.id.switch3),
            findViewById(R.id.switch4),
            findViewById(R.id.switch5)
        )

        buttonLink.setOnClickListener {
            Toast.makeText(this, "Base URL set to: ${editTextIP.text}", Toast.LENGTH_SHORT).show()
        }

        switches.forEachIndexed { index, switch ->
            val ledNum = index + 1
            switch.setOnCheckedChangeListener { _, isChecked ->
                val state = if (isChecked) "on" else "off"
                sendPostRequest(ledNum, state)
            }
        }
    }

    private fun sendPostRequest(ledNum: Int, state: String) {
        val baseUrl = editTextIP.text.toString().trim()
        if (baseUrl.isEmpty()) {
            Toast.makeText(this, "Please enter IP address", Toast.LENGTH_SHORT).show()
            return
        }

        // Ensure URL ends with /switch
        val targetUrl = if (baseUrl.endsWith("/switch")) baseUrl else "$baseUrl/switch"

        lifecycleScope.launch(Dispatchers.IO) {
            val result = try {
                val url = URL(targetUrl)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.doOutput = true
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                val body = "led=$ledNum&state=$state"
                val writer = OutputStreamWriter(conn.outputStream)
                writer.write(body)
                writer.flush()
                writer.close()

                val responseCode = conn.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(conn.inputStream))
                    val response = reader.readLine()
                    reader.close()
                    "Success: $response"
                } else {
                    "Error: $responseCode"
                }
            } catch (e: Exception) {
                "Exception: ${e.message}"
            }

            withContext(Dispatchers.Main) {
                textViewResponse.text = result
            }
        }
    }
}