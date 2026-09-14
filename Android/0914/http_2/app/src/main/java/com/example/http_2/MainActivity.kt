package com.example.http_2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var editTextIP: EditText
    private lateinit var textViewResponse: TextView
    private lateinit var switches: List<SwitchMaterial>
    private lateinit var ledImages: List<ImageView>

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

        ledImages = listOf(
            findViewById(R.id.imageLed1),
            findViewById(R.id.imageLed2),
            findViewById(R.id.imageLed3),
            findViewById(R.id.imageLed4),
            findViewById(R.id.imageLed5)
        )

        buttonLink.setOnClickListener {
            textViewResponse.text = "Home control link ok"
        }

        switches.forEachIndexed { index, switch ->
            val ledNum = index + 1
            switch.setOnCheckedChangeListener { _, isChecked ->
                val state = if (isChecked) "on" else "off"
                
                // 動態切換燈泡圖示 (開燈有光芒, 關燈沒有)
                if (isChecked) {
                    ledImages[index].setImageResource(R.drawable.ic_led_on)
                } else {
                    ledImages[index].setImageResource(R.drawable.ic_led_off)
                }

                val baseUrl = editTextIP.text.toString().trim()
                val targetUrl = if (baseUrl.endsWith("/switch")) baseUrl else "$baseUrl/switch"
                
                lifecycleScope.launch {
                    val res = httpPost(targetUrl, "led=$ledNum&state=$state")
                    textViewResponse.text = res
                }
            }
        }
    }

    private suspend fun httpPost(urlString: String, params: String): String {
        return withContext(Dispatchers.IO) {
            val sb = StringBuilder()
            var conn: HttpURLConnection? = null
            try {
                val url = URL(urlString)
                conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.connectTimeout = 5000
                conn.readTimeout = 5000
                conn.doOutput = true
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                val writer = OutputStreamWriter(conn.outputStream)
                writer.write(params)
                writer.flush()
                writer.close()

                if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(conn.inputStream))
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        sb.append(line)
                    }
                    reader.close()
                } else {
                    return@withContext "Error: ${conn.responseCode}"
                }
            } catch (e: MalformedURLException) {
                return@withContext "URL Error"
            } catch (e: IOException) {
                return@withContext "IO Error"
            } finally {
                conn?.disconnect()
            }
            sb.toString()
        }
    }
}