package com.example.http_1

import android.graphics.Color
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
import org.json.JSONObject
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    private lateinit var textViewData: TextView
    private lateinit var buttonGetData: Button
    private lateinit var buttonPostSet: Button
    private lateinit var buttonGetSet: Button
    private lateinit var editTextField1: EditText
    private lateinit var editTextField2: EditText

    companion object {

        private const val TAG = "ThingSpeak"

        // ThingSpeak API Server
        private const val WEB_ADDRESS =
            "https://api.thingspeak.com"

        // ★ 請換成你重新產生的 Write API Key
        private const val WRITE_API_KEY =
                    "GI5MENZRYXUAZY04"

        // ★ 請換成你重新產生的 Read API Key
        private const val READ_API_KEY =
                    "E56801DZUIC03MBS"

        // ThingSpeak Channel ID
        private const val CHANNEL_ID =
            "3490041"

        // ============================================================
        // Get Data 使用
        // results=2：讀取最近 2 筆
        // ============================================================
        private const val GET_FEEDS_URL =
            "$WEB_ADDRESS/channels/$CHANNEL_ID/feeds.json" +
                    "?api_key=$READ_API_KEY&results=2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->

            val systemBars =
                insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                )

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // ============================================================
        // View 初始化
        // ============================================================

        textViewData =
            findViewById(R.id.textViewData)

        buttonGetData =
            findViewById(R.id.buttonGetData)

        buttonPostSet =
            findViewById(R.id.buttonPostSet)

        buttonGetSet =
            findViewById(R.id.buttonGetSet)

        editTextField1 =
            findViewById(R.id.editTextField1)

        editTextField2 =
            findViewById(R.id.editTextField2)

        textViewData.movementMethod =
            ScrollingMovementMethod()

        // ============================================================
        // ① Get-Set
        //
        // 使用 HTTP GET 寫入 ThingSpeak
        //
        // 官方格式：
        //
        // https://api.thingspeak.com/update.json
        // ?api_key=WRITE_KEY
        // &field1=155
        // &field2=86
        //
        // ============================================================

        buttonGetSet.setOnClickListener {

            val field1 =
                editTextField1.text
                    .toString()
                    .trim()

            val field2 =
                editTextField2.text
                    .toString()
                    .trim()

            // 防止空白資料
            if (field1.isEmpty() || field2.isEmpty()) {

                textViewData.text =
                    "Field 1、Field 2 不可空白"

                return@setOnClickListener
            }

            // ========================================================
            // ★ 重要修正：
            // 對參數做 URL Encode
            // ========================================================

            val encodedField1 =
                URLEncoder.encode(
                    field1,
                    "UTF-8"
                )

            val encodedField2 =
                URLEncoder.encode(
                    field2,
                    "UTF-8"
                )

            val encodedApiKey =
                URLEncoder.encode(
                    WRITE_API_KEY,
                    "UTF-8"
                )

            // ========================================================
            // ★ 改用 update.json
            // ========================================================

            val urlString =
                "$WEB_ADDRESS/update.json" +
                        "?api_key=$encodedApiKey" +
                        "&field1=$encodedField1" +
                        "&field2=$encodedField2"

            Log.d(
                TAG,
                "GET URL = $urlString"
            )

            HttpGetUpdate(
                urlString
            ).start()
        }

        // ============================================================
        // ② Set-Post
        //
        // 使用 HTTP POST 寫入
        // ============================================================

        buttonPostSet.setOnClickListener {

            val field1 =
                editTextField1.text
                    .toString()
                    .trim()

            val field2 =
                editTextField2.text
                    .toString()
                    .trim()

            if (field1.isEmpty() || field2.isEmpty()) {

                textViewData.text =
                    "Field 1、Field 2 不可空白"

                return@setOnClickListener
            }

            HttpPostData(
                field1,
                field2
            ).start()
        }

        // ============================================================
        // ③ Get Data
        //
        // 從 ThingSpeak 讀取最近資料
        // ============================================================

        buttonGetData.setOnClickListener {

            textViewData.text =
                "正在讀取 ThingSpeak..."

            HttpGetFeeds(
                GET_FEEDS_URL
            ).start()
        }
    }


    // ================================================================
    //
    // Get-Set
    //
    // HTTP GET 寫入 ThingSpeak
    //
    // ================================================================

    private inner class HttpGetUpdate(
        private val targetUrl: String
    ) : Thread() {

        override fun run() {

            var resultText = ""

            var responseCode = 0

            try {

                Log.d(
                    TAG,
                    "GET Request = $targetUrl"
                )

                val url =
                    URL(targetUrl)

                val conn =
                    url.openConnection()
                            as HttpURLConnection

                // ====================================================
                // ★ 明確指定 GET
                // ====================================================

                conn.requestMethod =
                    "GET"

                // ====================================================
                // ★ 補 User-Agent
                //
                // 避免某些 HTTP Request 被伺服器拒絕
                // ====================================================

                conn.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 Android ThingSpeak App"
                )

                conn.setRequestProperty(
                    "Accept",
                    "application/json"
                )

                conn.connectTimeout =
                    10000

                conn.readTimeout =
                    10000

                conn.useCaches =
                    false

                conn.doInput =
                    true

                // ====================================================
                // 取得 HTTP Status Code
                // ====================================================

                responseCode =
                    conn.responseCode

                Log.d(
                    TAG,
                    "GET Response Code = $responseCode"
                )

                // ====================================================
                // HTTP 200
                // ====================================================

                if (
                    responseCode ==
                    HttpURLConnection.HTTP_OK
                ) {

                    val reader =
                        InputStreamReader(
                            conn.inputStream
                        )

                    resultText =
                        reader.readText()

                    reader.close()

                } else {

                    // =================================================
                    // ★ 錯誤時把 ThingSpeak 回傳內容也讀出來
                    // =================================================

                    val stream =
                        conn.errorStream

                    resultText =
                        if (stream != null) {

                            val reader =
                                InputStreamReader(
                                    stream
                                )

                            val error =
                                reader.readText()

                            reader.close()

                            error

                        } else {

                            "No error body"
                        }
                }

                conn.disconnect()

            } catch (e: Exception) {

                Log.e(
                    TAG,
                    "GET Update Error",
                    e
                )

                resultText =
                    e.message ?: "Unknown Error"
            }

            // ========================================================
            // 回 UI Thread
            // ========================================================

            val finalResult =
                resultText

            val finalCode =
                responseCode

            runOnUiThread {

                // ====================================================
                // 成功
                // ====================================================

                if (
                    finalCode ==
                    HttpURLConnection.HTTP_OK
                ) {

                    try {

                        // update.json 成功會回傳 JSON
                        val json =
                            JSONObject(
                                finalResult
                            )

                        val entryId =
                            json.optInt(
                                "entry_id",
                                0
                            )

                        val field1 =
                            json.optString(
                                "field1",
                                ""
                            )

                        val field2 =
                            json.optString(
                                "field2",
                                ""
                            )

                        textViewData.text =
                            "GET Update 成功\n" +
                                    "HTTP Code = $finalCode\n" +
                                    "write number = $entryId\n" +
                                    "Field 1 = $field1\n" +
                                    "Field 2 = $field2"

                        textViewData.setTextColor(
                            Color.parseColor(
                                "#3F51B5"
                            )
                        )

                        textViewData.textSize =
                            22f

                    } catch (e: Exception) {

                        textViewData.text =
                            "GET Update 成功\n" +
                                    "HTTP Code = $finalCode\n" +
                                    "Response:\n" +
                                    finalResult
                    }

                } else {

                    // =================================================
                    // HTTP 400 / 401 / 404...
                    // =================================================

                    textViewData.text =
                        "GET Update 失敗\n" +
                                "HTTP Code = $finalCode\n" +
                                "Server Response:\n" +
                                finalResult
                }
            }
        }
    }


    // ================================================================
    //
    // Set-Post
    //
    // HTTP POST 寫入 ThingSpeak
    //
    // ================================================================

    private inner class HttpPostData(
        private val field1: String,
        private val field2: String
    ) : Thread() {

        override fun run() {

            var resultText = ""

            var responseCode = 0

            try {

                // ====================================================
                // POST 使用 update.json
                // ====================================================

                val targetUrl =
                    "$WEB_ADDRESS/update.json"

                val url =
                    URL(targetUrl)

                val conn =
                    url.openConnection()
                            as HttpURLConnection

                conn.requestMethod =
                    "POST"

                conn.doOutput =
                    true

                conn.doInput =
                    true

                conn.connectTimeout =
                    10000

                conn.readTimeout =
                    10000

                conn.useCaches =
                    false

                // ====================================================
                // POST Content-Type
                // ====================================================

                conn.setRequestProperty(
                    "Content-Type",
                    "application/x-www-form-urlencoded"
                )

                conn.setRequestProperty(
                    "Accept",
                    "application/json"
                )

                conn.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 Android ThingSpeak App"
                )

                // ====================================================
                // POST Body
                // ====================================================

                val postData =
                    "api_key=" +
                            URLEncoder.encode(
                                WRITE_API_KEY,
                                "UTF-8"
                            ) +
                            "&field1=" +
                            URLEncoder.encode(
                                field1,
                                "UTF-8"
                            ) +
                            "&field2=" +
                            URLEncoder.encode(
                                field2,
                                "UTF-8"
                            )

                Log.d(
                    TAG,
                    "POST Data = $postData"
                )

                // ====================================================
                // 寫出 POST Body
                // ====================================================

                val writer =
                    OutputStreamWriter(
                        conn.outputStream
                    )

                writer.write(
                    postData
                )

                writer.flush()

                writer.close()

                // ====================================================
                // HTTP Status
                // ====================================================

                responseCode =
                    conn.responseCode

                Log.d(
                    TAG,
                    "POST Response Code = $responseCode"
                )

                if (
                    responseCode ==
                    HttpURLConnection.HTTP_OK
                ) {

                    val reader =
                        InputStreamReader(
                            conn.inputStream
                        )

                    resultText =
                        reader.readText()

                    reader.close()

                } else {

                    val stream =
                        conn.errorStream

                    resultText =
                        if (stream != null) {

                            val reader =
                                InputStreamReader(
                                    stream
                                )

                            val error =
                                reader.readText()

                            reader.close()

                            error

                        } else {

                            "No error body"
                        }
                }

                conn.disconnect()

            } catch (e: Exception) {

                Log.e(
                    TAG,
                    "POST Error",
                    e
                )

                resultText =
                    e.message ?: "Unknown Error"
            }

            val finalResult =
                resultText

            val finalCode =
                responseCode

            runOnUiThread {

                if (
                    finalCode ==
                    HttpURLConnection.HTTP_OK
                ) {

                    try {

                        val json =
                            JSONObject(
                                finalResult
                            )

                        val entryId =
                            json.optInt(
                                "entry_id",
                                0
                            )

                        val f1 =
                            json.optString(
                                "field1",
                                ""
                            )

                        val f2 =
                            json.optString(
                                "field2",
                                ""
                            )

                        textViewData.text =
                            "POST Update 成功\n" +
                                    "HTTP Code = $finalCode\n" +
                                    "write number = $entryId\n" +
                                    "Field 1 = $f1\n" +
                                    "Field 2 = $f2"

                    } catch (e: Exception) {

                        textViewData.text =
                            "POST Update 成功\n" +
                                    "HTTP Code = $finalCode\n" +
                                    finalResult
                    }

                } else {

                    textViewData.text =
                        "POST Update 失敗\n" +
                                "HTTP Code = $finalCode\n" +
                                finalResult
                }
            }
        }
    }


    // ================================================================
    //
    // Get Data
    //
    // 讀取 ThingSpeak feeds.json
    //
    // ================================================================

    private inner class HttpGetFeeds(
        private val targetUrl: String
    ) : Thread() {

        override fun run() {

            var resultText = ""

            var responseCode = 0

            try {

                Log.d(
                    TAG,
                    "Feeds URL = $targetUrl"
                )

                val url =
                    URL(targetUrl)

                val conn =
                    url.openConnection()
                            as HttpURLConnection

                conn.requestMethod =
                    "GET"

                conn.connectTimeout =
                    10000

                conn.readTimeout =
                    10000

                conn.useCaches =
                    false

                conn.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 Android ThingSpeak App"
                )

                conn.setRequestProperty(
                    "Accept",
                    "application/json"
                )

                responseCode =
                    conn.responseCode

                if (
                    responseCode ==
                    HttpURLConnection.HTTP_OK
                ) {

                    val reader =
                        InputStreamReader(
                            conn.inputStream
                        )

                    resultText =
                        reader.readText()

                    reader.close()

                } else {

                    val stream =
                        conn.errorStream

                    resultText =
                        if (stream != null) {

                            val reader =
                                InputStreamReader(
                                    stream
                                )

                            val error =
                                reader.readText()

                            reader.close()

                            error

                        } else {

                            "No error body"
                        }
                }

                conn.disconnect()

            } catch (e: Exception) {

                Log.e(
                    TAG,
                    "Get Feeds Error",
                    e
                )

                resultText =
                    e.message ?: "Unknown Error"
            }

            val finalResult =
                resultText

            val finalCode =
                responseCode

            runOnUiThread {

                if (
                    finalCode ==
                    HttpURLConnection.HTTP_OK
                ) {

                    try {

                        val jsonObject =
                            JSONObject(
                                finalResult
                            )

                        val feeds =
                            jsonObject.getJSONArray(
                                "feeds"
                            )

                        if (
                            feeds.length() > 0
                        ) {

                            // =========================================
                            // 最後一筆 = 最新資料
                            // =========================================

                            val lastFeed =
                                feeds.getJSONObject(
                                    feeds.length() - 1
                                )

                            val field1 =
                                lastFeed.optString(
                                    "field1",
                                    "0"
                                )

                            val field2 =
                                lastFeed.optString(
                                    "field2",
                                    "0"
                                )

                            val entryId =
                                lastFeed.optInt(
                                    "entry_id",
                                    0
                                )

                            // =========================================
                            // 回填 EditText
                            // =========================================

                            editTextField1.setText(
                                field1
                            )

                            editTextField2.setText(
                                field2
                            )

                            // =========================================
                            // 顯示 write number
                            // =========================================

                            textViewData.text =
                                "Get Data 成功\n" +
                                        "write number = $entryId\n" +
                                        "Field 1 = $field1\n" +
                                        "Field 2 = $field2"

                            textViewData.setTextColor(
                                Color.parseColor(
                                    "#3F51B5"
                                )
                            )

                            textViewData.textSize =
                                22f

                        } else {

                            textViewData.text =
                                "ThingSpeak 目前沒有資料"
                        }

                    } catch (e: Exception) {

                        textViewData.text =
                            "JSON 解析失敗\n" +
                                    "${e.message}\n\n" +
                                    finalResult
                    }

                } else {

                    textViewData.text =
                        "Get Data 失敗\n" +
                                "HTTP Code = $finalCode\n" +
                                finalResult
                }
            }
        }
    }
}