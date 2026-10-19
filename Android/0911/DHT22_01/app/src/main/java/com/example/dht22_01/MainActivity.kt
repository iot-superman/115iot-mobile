package com.example.dht22_01

import android.graphics.Color
import android.os.Bundle
import android.os.Build
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var tvTemp: TextView
    private lateinit var tvHum: TextView
    private lateinit var btnReload: Button
    private lateinit var lineChart: LineChart
    private lateinit var tvApiStatus: TextView
    private lateinit var tvStatus: TextView
    private lateinit var client: Mqtt3AsyncClient

    private val httpClient = OkHttpClient()

    companion object {
        private const val TAG = "MainActivity"
        private const val SERVER_HOST = "192.168.63.57"  //90:teacher
        private const val SERVER_PORT = 1883
        private const val TOPIC = "dht22/311"
        private const val USERNAME = "cubie"
        private const val PASSWORD = "1234"
        private const val HISTORY_API_URL = "http://192.168.63.7:8080/api/chart/311/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main) //使用的布局文件

        tvTemp = findViewById(R.id.tvTemp)
        tvHum = findViewById(R.id.tvHum)
        btnReload = findViewById(R.id.btnReload)
        lineChart = findViewById(R.id.lineChart)
        tvApiStatus = findViewById(R.id.tvApiStatus)
        tvStatus = findViewById(R.id.tvStatus)

        setupChart()
        btnReload.setOnClickListener {
            loadHistoryData()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        connectMQTT()
        loadHistoryData()
    }

    private fun connectMQTT() {
        val mqttHost = resolveServerHost()

        client = MqttClient.builder()
            .automaticReconnectWithDefaultConfig()
            .addDisconnectedListener { context ->
                Log.e(TAG, "MQTT 連線中斷，將自動重連", context.cause)
                setStatus(getString(R.string.status_disconnected))
            }
            .useMqttVersion3()
            .identifier("android-client-${System.currentTimeMillis()}")
            .serverHost(mqttHost)
            .serverPort(SERVER_PORT)
            .buildAsync()

        val connectBuilder = client.connectWith()
            .cleanSession(false)
            .keepAlive(30)
        if (USERNAME.isNotBlank()) {
            connectBuilder
                .simpleAuth()
                .username(USERNAME)
                .password(PASSWORD.toByteArray())
                .applySimpleAuth()
        }

        connectBuilder.send()
            .whenComplete { _, throwable ->
                if (throwable != null) {
                    Log.e(TAG, "MQTT 連線失敗", throwable)
                    setStatus(getString(R.string.status_connect_failed, throwable.message ?: "unknown"))
                } else {
                    Log.d(TAG, "MQTT 連線成功")
                    setStatus(getString(R.string.status_connected, mqttHost, SERVER_PORT.toString()))
                    subscribeTopic()
                }
            }
    }

    private fun subscribeTopic() {
        client.subscribeWith()
            .topicFilter(TOPIC)
            .qos(MqttQos.AT_LEAST_ONCE)
            .callback { publish ->
                val bytes = publish.payloadAsBytes
                val payload = String(bytes, StandardCharsets.UTF_8)
                Log.d(TAG, "Received message: $payload")
                println("Received message: $payload")
                setStatus(getString(R.string.status_message, currentTimeText(), payload))
                updateSensorViews(payload)
                loadHistoryData()
            }
            .send()
            .whenComplete { _, throwable ->
                if (throwable != null) {
                    Log.e(TAG, "訂閱主題失敗: $TOPIC", throwable)
                    setStatus(getString(R.string.status_subscribe_failed, TOPIC, throwable.message ?: "unknown"))
                } else {
                    Log.d(TAG, "已訂閱主題: $TOPIC")
                    setStatus(getString(R.string.status_subscribed, TOPIC))
                }
            }
    }
    private fun loadHistoryData() {
        setApiStatus(getString(R.string.api_loading))

        Thread {
            try {
                val request = Request.Builder()
                    .url(HISTORY_API_URL)
                    .build()
                val response = httpClient.newCall(request).execute()

                response.use {
                    val body = it.body?.string() ?: return@Thread
                    Log.d("myHTTP", "Response: $body")
                    println("Response body: $body")
                    showChart(body)
                }
            } catch (e: Exception) {
                Log.e(TAG, "API 讀取失敗", e)
                setApiStatus(getString(R.string.api_http_error, e.message ?: "unknown"))
            }
        }.start()
    }
    private fun updateSensorViews(payload: String) {
        try {
            val json = JSONObject(payload)
            val temperature = json.optString("temperature", "—")
            val humidity = json.optString("humidity", "—")

            runOnUiThread {
                tvTemp.text = getString(R.string.temperature_value, temperature)
                tvHum.text = getString(R.string.humidity_value, humidity)
            }
        } catch (e: Exception) {
            Log.e(TAG, "JSON 解析失敗: $payload", e)
            setStatus(getString(R.string.status_parse_failed, currentTimeText(), payload))
        }
    }

    private fun showChart(responseBody: String) {
        val json = JSONObject(responseBody)
        val labels = json.optJSONArray("labels") ?: JSONArray()
        val temperatures = json.optJSONArray("temperatures") ?: JSONArray()
        val humidities = json.optJSONArray("humidities")

        println("Labels: $labels")
        println("Temperatures: $temperatures")
        println("Humidities: ${humidities ?: "null"}")

        val pointCount = minOf(labels.length(), temperatures.length())
        if (pointCount == 0) {
            setApiStatus(getString(R.string.api_empty))
            return
        }

        val chartLabels = mutableListOf<String>()
        val temperatureEntries = ArrayList<Entry>(pointCount)
        val humidityEntries = ArrayList<Entry>()
        val humidityCount = humidities?.length() ?: 0

        for (index in 0 until pointCount) {
            chartLabels += labels.optString(index, index.toString())
            temperatureEntries += Entry(index.toFloat(), temperatures.optDouble(index, 0.0).toFloat())
            if (index < humidityCount) {
                humidityEntries += Entry(index.toFloat(), humidities?.optDouble(index, 0.0)?.toFloat() ?: 0f)
            }
        }

        println("labels2: $chartLabels")
        println("entries2: $temperatureEntries")
        if (humidityEntries.isNotEmpty()) {
            println("humidityEntries2: $humidityEntries")
        }

        val latestTemperature = temperatures.optDouble(pointCount - 1, Double.NaN)
        val latestHumidity = if (humidityCount > 0) {
            humidities?.optDouble(minOf(pointCount, humidityCount) - 1, Double.NaN) ?: Double.NaN
        } else {
            Double.NaN
        }

        runOnUiThread {
            updateLatestValuesFromApi(latestTemperature, latestHumidity)
            renderChart(chartLabels, temperatureEntries, humidityEntries)
            tvApiStatus.text = getString(R.string.api_success, currentTimeText(), pointCount)
        }
    }

    private fun setupChart() {
        lineChart.description.isEnabled = false
        lineChart.setNoDataText(getString(R.string.api_idle))
        lineChart.setBackgroundColor(Color.WHITE)
        lineChart.setDrawGridBackground(false)
        lineChart.setDrawBorders(false)
        lineChart.axisRight.isEnabled = false
        lineChart.legend.isEnabled = true
        lineChart.legend.textColor = Color.DKGRAY
        lineChart.legend.textSize = 11f
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(true)
        lineChart.extraBottomOffset = 12f

        lineChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
            textColor = Color.DKGRAY
            textSize = 10f
            setDrawAxisLine(true)
            axisLineColor = Color.GRAY
            axisLineWidth = 1f
            setDrawGridLines(true)
            gridColor = Color.LTGRAY
            gridLineWidth = 0.8f
            labelCount = 6
            setAvoidFirstLastClipping(true)
        }

        lineChart.axisLeft.apply {
            granularity = 1f
            textColor = Color.DKGRAY
            textSize = 10f
            setDrawAxisLine(true)
            axisLineColor = Color.GRAY
            axisLineWidth = 1f
            setDrawGridLines(true)
            gridColor = Color.LTGRAY
            gridLineWidth = 0.8f
        }
    }

    private fun renderChart(
        labels: List<String>,
        temperatureEntries: List<Entry>,
        humidityEntries: List<Entry>
    ) {
        val temperatureDataSet = LineDataSet(temperatureEntries, "Temperature").apply {
            color = Color.rgb(102, 229, 255)
            setCircleColor(Color.rgb(102, 229, 255))
            lineWidth = 2.2f
            circleRadius = 3.5f
            setDrawCircleHole(true)
            circleHoleRadius = 1.8f
            circleHoleColor = Color.WHITE
            valueTextSize = 6f
            valueTextColor = Color.DKGRAY
            setDrawHighlightIndicators(true)
            mode = LineDataSet.Mode.LINEAR
        }

        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        lineChart.xAxis.labelRotationAngle = -45f
        lineChart.xAxis.axisMinimum = 0f
        lineChart.xAxis.axisMaximum = (labels.size - 1).toFloat()

        val lineData = if (humidityEntries.isNotEmpty()) {
            val humidityDataSet = LineDataSet(humidityEntries, "Humidity").apply {
                color = Color.rgb(63, 81, 181)
                setCircleColor(Color.rgb(63, 81, 181))
                lineWidth = 2f
                circleRadius = 3f
                setDrawCircleHole(true)
                circleHoleRadius = 1.5f
                circleHoleColor = Color.WHITE
                valueTextSize = 6f
                valueTextColor = Color.DKGRAY
                mode = LineDataSet.Mode.LINEAR
            }
            LineData(temperatureDataSet, humidityDataSet)
        } else {
            LineData(temperatureDataSet)
        }

        lineData.setDrawValues(true)
        lineChart.data = lineData
        lineChart.animateX(300)
        lineChart.invalidate()
    }

    private fun updateLatestValuesFromApi(temperature: Double, humidity: Double) {
        if (!temperature.isNaN()) {
            tvTemp.text = getString(R.string.temperature_value, String.format(Locale.US, "%.1f", temperature))
        }
        if (!humidity.isNaN()) {
            tvHum.text = getString(R.string.humidity_value, String.format(Locale.US, "%.1f", humidity))
        }
    }

    private fun setStatus(status: String) {
        runOnUiThread {
            tvStatus.text = status
        }
    }

    private fun setApiStatus(status: String) {
        runOnUiThread {
            tvApiStatus.text = status
        }
    }

    private fun currentTimeText(): String {
        return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
    }

    private fun resolveServerHost(): String {
        return if (isRunningOnEmulator()) "10.0.2.2" else SERVER_HOST
    }

    private fun isRunningOnEmulator(): Boolean {
        return Build.FINGERPRINT.contains("generic", ignoreCase = true) ||
            Build.MODEL.contains("Emulator", ignoreCase = true) ||
            Build.MODEL.contains("Android SDK built for", ignoreCase = true) ||
            Build.MANUFACTURER.contains("Genymotion", ignoreCase = true) ||
            Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") ||
            Build.PRODUCT == "google_sdk"
    }

    override fun onDestroy() {
        if (::client.isInitialized) {
            client.disconnect()
        }
        super.onDestroy()
    }
}