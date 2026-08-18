package com.example.listview5

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private lateinit var listViewSensor: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listViewSensor = findViewById<ListView>(R.id.listView_Id)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensorData = sensorManager.getSensorList(Sensor.TYPE_ALL)
        val listData = mutableListOf<String>()

        for (sensor in sensorData) {
            listData.add("${sensor.type.toString()} : ${sensor.name} - ${sensor.vendor}")
        }

        title = "Sensor number : ${listData.size}"

        val adapter = ArrayAdapter<String>(this@MainActivity, R.layout.item_layout, R.id.textView_itemData, listData)
        listViewSensor.adapter = adapter
        listViewSensor.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                 val  sensor =sensorData.get(position)
                 when(sensor.type){
                     Sensor.TYPE_LIGHT ->{
                         val intent = Intent(this@MainActivity, LightActivity::class.java)
                         startActivity(intent)

                     }
                     Sensor.TYPE_PROXIMITY ->{
                         val intent = Intent(this@MainActivity, ProxiActivity::class.java)
                         startActivity(intent)

                     }
                     Sensor.TYPE_ACCELEROMETER->{
                         val intent = Intent(this@MainActivity, AccActivity::class.java)
                         startActivity(intent)

                     }
                }
            }
        }
    }
}