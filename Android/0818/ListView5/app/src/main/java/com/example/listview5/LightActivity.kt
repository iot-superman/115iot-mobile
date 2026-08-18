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

class LightActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var textViewData: TextView
    private lateinit var imageViewLight: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_light)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        title = "Light sensor"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textViewData = findViewById(R.id.textViewData)
        imageViewLight = findViewById(R.id.imageViewLight)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val value = event?.values?.get(0)
        val data = "Lux value = ${event?.values?.get(0)}"
        textViewData.text = data
        
        if (value != null) {
            if (value < 50) {
                // 請確保你的 drawable 資料夾中有 dark_light 這個檔案
                 imageViewLight.setImageResource(R.drawable.dark_light)

            } else {
                // 請確保你的 drawable 資料夾中有 imageslight 這個檔案
                 imageViewLight.setImageResource(R.drawable.light_light)
                

            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
