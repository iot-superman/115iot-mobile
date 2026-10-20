package com.example.iotrelay

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.eclipse.paho.client.mqttv3.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var mqttClient: MqttAsyncClient

    private lateinit var btnRelay1: Button
    private lateinit var btnRelay2: Button
    private lateinit var btnRelay3: Button
    private lateinit var btnRelay4: Button
    private lateinit var txtMqttStatus: TextView

    private val relayState = mutableMapOf(
        "relay1" to "off",
        "relay2" to "off",
        "relay3" to "off",
        "relay4" to "off"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnRelay1 = findViewById(R.id.btnRelay1)
        btnRelay2 = findViewById(R.id.btnRelay2)
        btnRelay3 = findViewById(R.id.btnRelay3)
        btnRelay4 = findViewById(R.id.btnRelay4)
        txtMqttStatus = findViewById(R.id.txtMqttStatus)

        btnRelay1.setOnClickListener { toggleRelay("relay1") }
        btnRelay2.setOnClickListener { toggleRelay("relay2") }
        btnRelay3.setOnClickListener { toggleRelay("relay3") }
        btnRelay4.setOnClickListener { toggleRelay("relay4") }

        updateButtons()
        connectMqtt()
    }

    private fun connectMqtt() {
        val clientId = MqttConfig.CLIENT_ID_PREFIX + System.currentTimeMillis()

        mqttClient = MqttAsyncClient(
            MqttConfig.SERVER_URI,
            clientId,
            null
        )

        val options = MqttConnectOptions().apply {
            isCleanSession = true
            userName = MqttConfig.USERNAME
            password = MqttConfig.PASSWORD.toCharArray()
            isAutomaticReconnect = true
            connectionTimeout = 10
        }

        mqttClient.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                runOnUiThread {
                    txtMqttStatus.text = "MQTT：已連線"
                }

                mqttClient.subscribe(MqttConfig.TOPIC_STATUS, 1)
            }

            override fun connectionLost(cause: Throwable?) {
                runOnUiThread {
                    txtMqttStatus.text = "MQTT：連線中斷"
                }
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                val payload = message.toString()

                try {
                    val json = JSONObject(payload)

                    runOnUiThread {
                        relayState["relay1"] = json.optString("relay1", relayState.getValue("relay1"))
                        relayState["relay2"] = json.optString("relay2", relayState.getValue("relay2"))
                        relayState["relay3"] = json.optString("relay3", relayState.getValue("relay3"))
                        relayState["relay4"] = json.optString("relay4", relayState.getValue("relay4"))
                        updateButtons()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
            }
        })

        mqttClient.connect(options, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                runOnUiThread {
                    txtMqttStatus.text = "MQTT：連線成功"
                }
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                runOnUiThread {
                    txtMqttStatus.text = "MQTT：連線失敗"
                }
            }
        })
    }

    private fun toggleRelay(relayName: String) {
        val current = relayState[relayName]

        relayState[relayName] = if (current == "on") {
            "off"
        } else {
            "on"
        }

        updateButtons()
        publishRelayControl()
    }

    private fun publishRelayControl() {
        if (!mqttClient.isConnected) {
            txtMqttStatus.text = "MQTT：尚未連線，無法送出"
            return
        }

        val json = JSONObject()
        json.put("relay1", relayState["relay1"])
        json.put("relay2", relayState["relay2"])
        json.put("relay3", relayState["relay3"])
        json.put("relay4", relayState["relay4"])

        val message = MqttMessage(json.toString().toByteArray())
        message.qos = 1
        message.isRetained = false

        mqttClient.publish(MqttConfig.TOPIC_CONTROL, message)
    }

    private fun updateButtons() {
        btnRelay1.text = "relay1：" + stateText(relayState["relay1"])
        btnRelay2.text = "relay2：" + stateText(relayState["relay2"])
        btnRelay3.text = "relay3：" + stateText(relayState["relay3"])
        btnRelay4.text = "relay4：" + stateText(relayState["relay4"])
    }

    private fun stateText(state: String?): String {
        return if (state == "on") {
            "開啟"
        } else {
            "關閉"
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            if (::mqttClient.isInitialized && mqttClient.isConnected) {
                mqttClient.disconnect()
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}