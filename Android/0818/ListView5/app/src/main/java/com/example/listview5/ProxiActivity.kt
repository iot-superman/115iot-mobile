package com.example.listview5

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProxiActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager

    // 接近感測器
    private var proxiSensor: Sensor? = null

    private lateinit var textViewProxiData: TextView
    private lateinit var imageViewProxiPic: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_proxi)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->

            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        title = "Proximity sensor"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 取得畫面元件
        textViewProxiData =
            findViewById(R.id.textView_proxiData)

        imageViewProxiPic =
            findViewById(R.id.imageView_proxiPic)

        // =========================================
        // 取得 SensorManager
        // =========================================
        sensorManager =
            getSystemService(SENSOR_SERVICE) as SensorManager

        // =========================================
        // 取得接近感測器
        // =========================================
        proxiSensor =
            sensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY
            )

        // =========================================
        // 檢查手機是否真的有 Proximity Sensor
        // =========================================
        if (proxiSensor == null) {

            textViewProxiData.text =
                "此手機沒有 Proximity Sensor"

        } else {

            // 顯示感測器資訊
            textViewProxiData.text =
                "感測器：${proxiSensor?.name}\n" +
                        "Maximum Range：${proxiSensor?.maximumRange}"
        }
    }


    // ==================================================
    // Activity 回到前景
    // 開始監聽 Proximity Sensor
    // ==================================================
    override fun onResume() {
        super.onResume()

        proxiSensor?.let {

            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }


    // ==================================================
    // Activity 離開前景
    // 停止監聽 Sensor
    // ==================================================
    override fun onPause() {
        super.onPause()

        sensorManager.unregisterListener(this)
    }


    // ==================================================
    // Proximity Sensor 數值發生變化
    // ==================================================
    override fun onSensorChanged(event: SensorEvent?) {

        // 確認事件不是 null
        if (event == null) {
            return
        }

        // 確認是不是 Proximity Sensor
        if (event.sensor.type != Sensor.TYPE_PROXIMITY) {
            return
        }

        // =========================================
        // 取得目前 Proximity Sensor 原始數值
        // =========================================
        val distance = event.values[0]

        // =========================================
        // 取得此感測器 Maximum Range
        // =========================================
        val maxRange = event.sensor.maximumRange


        // =========================================
        // 判斷 Near / Far
        //
        // 一般 Android Proximity Sensor：
        //
        // distance < maxRange
        //     → Near
        //
        // distance >= maxRange
        //     → Far
        //
        // 例如：
        //
        // 0.0 → Near
        // 5.0 → Far
        //
        // 但不同手機可能不同
        // =========================================

        val isNear = distance < maxRange


        if (isNear) {

            // =====================================
            // Near
            // =====================================

            imageViewProxiPic.setImageResource(
                android.R.drawable.presence_video_busy
            )

            imageViewProxiPic.scaleX = 1.5f
            imageViewProxiPic.scaleY = 1.5f

            textViewProxiData.text =
                "狀態：靠近 (Near)\n" +
                        "Sensor Value：$distance\n" +
                        "Maximum Range：$maxRange"

        } else {

            // =====================================
            // Far
            // =====================================

            imageViewProxiPic.setImageResource(
                android.R.drawable.presence_video_online
            )

            imageViewProxiPic.scaleX = 1.0f
            imageViewProxiPic.scaleY = 1.0f

            textViewProxiData.text =
                "狀態：遠離 (Far)\n" +
                        "Sensor Value：$distance\n" +
                        "Maximum Range：$maxRange"
        }
    }


    // ==================================================
    // 感測器精準度改變
    // 這個範例不需要處理
    // ==================================================
    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int
    ) {
    }


    // ==================================================
    // ActionBar 返回
    // ==================================================
    override fun onSupportNavigateUp(): Boolean {

        finish()

        return true
    }
}