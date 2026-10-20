package com.example.iotrelay

object MqttConfig {
    const val SERVER_URI = "tcp://192.168.63.57:1883"
    const val USERNAME = "cubie"
    const val PASSWORD = "1234"

    const val TOPIC_CONTROL = "relay_control/teacher"
    const val TOPIC_STATUS = "relay_status/teacher"

    const val CLIENT_ID_PREFIX = "Android_Iotrelay_"
}