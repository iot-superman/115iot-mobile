package com.example.http_1

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {
    private lateinit var textViewData: TextView
    private lateinit var buttonGetData: Button
    private lateinit var buttonPostSet: Button
    private lateinit var buttonGetSet: Button
    private lateinit var editTextField2: EditText
    private lateinit var editTextField1: EditText

    private val client = OkHttpClient()

    companion object {
        private const val webAddress = "https://api.thingspeak.com/"
        private const val writeApiKey = "GI5MENZRYXUAZY04"
        private const val readApiKey = "E56801DZUIC03MBS"
        private const val channelId = "3277252"
        
        // 寫入網址
        private const val getUpdateUrl = "${webAddress}update?api_key=$writeApiKey"
        
        // 讀取網址 (最後 3 筆)
        private const val getField1Data = "${webAddress}channels/$channelId/fields/1.json?api_key=$readApiKey&results=3"
        private const val getField2Data = "${webAddress}channels/$channelId/fields/2.json?api_key=$readApiKey&results=3"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewData = findViewById(R.id.textViewData)
        textViewData.movementMethod = ScrollingMovementMethod()
        buttonGetData = findViewById(R.id.buttonGetData)
        buttonPostSet = findViewById(R.id.buttonPostSet)
        buttonGetSet = findViewById(R.id.buttonGetSet)
        editTextField1 = findViewById(R.id.editTextField1)
        editTextField2 = findViewById(R.id.editTextField2)

        // 使用 GET 方式寫入 (Update)
        buttonGetSet.setOnClickListener {
            val f1 = editTextField1.text.toString()
            val f2 = editTextField2.text.toString()
            val url = "$getUpdateUrl&field1=$f1&field2=$f2"
            executeRequest(Request.Builder().url(url).build(), "GET Update")
        }

        // 使用 POST 方式寫入 (Update)
        buttonPostSet.setOnClickListener {
            val f1 = editTextField1.text.toString()
            val f2 = editTextField2.text.toString()
            val formBody = FormBody.Builder()
                .add("api_key", writeApiKey)
                .add("field1", f1)
                .add("field2", f2)
                .build()
            val request = Request.Builder()
                .url("${webAddress}update")
                .post(formBody)
                .build()
            executeRequest(request, "POST Update")
        }

        // 讀取資料 (Read Last 3)
        buttonGetData.setOnClickListener {
            lifecycleScope.launch {
                val data1 = fetchData(getField1Data)
                val data2 = fetchData(getField2Data)
                textViewData.text = "Field 1 (Last 3):\n$data1\n\nField 2 (Last 3):\n$data2"
            }
        }
    }


    inner private class HttpGetData() : Thread() {

        override fun run() {

            thingSpeakUrl.append(webAddress)
            thingSpeakUrl.append(
                getApiKey + field1 + field1Data + field2 + field2Data
            )

            Log.d("main", "thingSpeakURL = $thingSpeakUrl")

            try {
                url = URL(thingSpeakUrl.toString())
                conn = url.openConnection() as HttpURLConnection

                conn.requestMethod = "GET"
                conn.connectTimeout = 10000
                conn.readTimeout = 10000

                code = conn.responseCode

                Log.d("main", "get code = $code")

            } catch (e: MalformedURLException) {

                Log.d("main", "mal error : $e")

            } catch (e: IOException) {

                Log.d("main", "IO error : $e")
            }

            if (code == HttpURLConnection.HTTP_OK) {

                val inputStream = conn.getInputStream()
                val reader = InputStreamReader(inputStream)

                getData = reader.readText()

                Log.d("main", "getData = $getData")

                inputStream.close()

                runOnUiThread {

                    textViewData.text = "write number = $getData "
                }
            }
        }
    }




    // 請將 MainActivity.kt 中的 executeRequest 方法稍微修改，加入 Log 輸出
    private fun executeRequest(request: Request, actionName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // 輸出網址到 Logcat，方便測試
                Log.d("main", "ThingSpeak URL: ${request.url}")

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string() ?: "Empty"
                withContext(Dispatchers.Main) {
                    // 如果是 0，代表被伺服器拒絕（通常是太頻繁）
                    textViewData.text = "$actionName Result: $responseBody"
                    if (responseBody == "0") {
                        textViewData.append("\n(請等待 15 秒後再試)")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    textViewData.text = "$actionName Error: ${e.message}"
                }
            }
        }
    }

    private suspend fun fetchData(url: String): String = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            response.body?.string() ?: "No data"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}
