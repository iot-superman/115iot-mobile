package com.example.listview5

import android.content.pm.ActivityInfo
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
import java.util.Locale
import kotlin.math.abs

class AccActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accSensor: Sensor? = null
    
    private lateinit var textViewAccData: TextView
    private lateinit var imageViewAccPic: ImageView
    
    private lateinit var ivUp: ImageView
    private lateinit var ivDown: ImageView
    private lateinit var ivLeft: ImageView
    private lateinit var ivRight: ImageView
    private lateinit var ivZin: ImageView
    private lateinit var ivZout: ImageView
    
    // 顯示門檻值
    private val showThreshold = 4.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
        
        ivUp = findViewById(R.id.iv_up)
        ivDown = findViewById(R.id.iv_down)
        ivLeft = findViewById(R.id.iv_left)
        ivRight = findViewById(R.id.iv_right)
        ivZin = findViewById(R.id.iv_z_in)
        ivZout = findViewById(R.id.iv_z_out)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        accSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
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
            
            textViewAccData.text = String.format(Locale.US, "X: %7.2f\nY: %7.2f\nZ: %7.2f", x, y, z)
            
            // 中央圖片旋轉與縮放
            imageViewAccPic.rotation = -x * 5
            var scaleFactor = (z / 9.8f) + 0.5f
            if (scaleFactor < 0.3f) scaleFactor = 0.3f
            if (scaleFactor > 2.0f) scaleFactor = 2.0f
            imageViewAccPic.scaleX = scaleFactor
            imageViewAccPic.scaleY = scaleFactor

            // 根據數值動態控制箭頭透明度
            updateArrowVisibility(x, y, z)
        }
    }

    private fun updateArrowVisibility(x: Float, y: Float, z: Float) {
        // X 軸 (左/右)
        ivLeft.alpha = if (x > showThreshold) 1.0f else 0.0f
        ivRight.alpha = if (x < -showThreshold) 1.0f else 0.0f

        // Y 軸 (上/下)
        ivUp.alpha = if (y > showThreshold) 1.0f else 0.0f
        ivDown.alpha = if (y < -showThreshold) 1.0f else 0.0f

        // Z 軸 (進/出) - 以重力 9.8 為基準的偏差
        val zDiff = z - 9.8f
        ivZout.alpha = if (zDiff > showThreshold) 1.0f else 0.0f
        ivZin.alpha = if (zDiff < -showThreshold) 1.0f else 0.0f
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
