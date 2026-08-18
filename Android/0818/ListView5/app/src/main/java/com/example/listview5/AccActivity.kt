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

class AccActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accSensor: Sensor? = null
    private lateinit var textViewAccData: TextView
    private lateinit var imageViewAccPic: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_acc)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        title = "ACC sensor"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textViewAccData = findViewById(R.id.textView_accData)
        imageViewAccPic = findViewById(R.id.imageView_accPic)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        accSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            
            textViewAccData.text = String.format("X: %.2f\nY: %.2f\nZ: %.2f", x, y, z)
            
            // X 軸控制旋轉
            imageViewAccPic.rotation = -x * 5
            
            // Z 軸控制縮放 (Scale)
            // 當手機平放時 Z 約為 9.8，垂直時 Z 約為 0
            // 我們將 Z 值映射到縮放比例，例如 0.5 到 2.0 之間
            var scaleFactor = (z / 9.8f) + 0.5f
            
            // 限制縮放範圍，避免圖片過大或消失
            if (scaleFactor < 0.3f) scaleFactor = 0.3f
            if (scaleFactor > 2.5f) scaleFactor = 2.5f
            
            imageViewAccPic.scaleX = scaleFactor
            imageViewAccPic.scaleY = scaleFactor
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
