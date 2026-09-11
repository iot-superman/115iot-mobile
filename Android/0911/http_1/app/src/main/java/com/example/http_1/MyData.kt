package com.example.http_1

data class MyData_1(
    val channel: Channel,
    val feeds: List<Feed_1>
)
data class MyData_2(
    val channel: Channel,
    val feeds: List<Feed_2>
)

data class Channel(
    val id: Int,
    val name: String,
    val description: String,
    val latitude: String,
    val longitude: String,
    val field1: String,
    val field2: String,
    val created_at: String,
    val updated_at: String,
    val last_entry_id: Int
)

data class Feed_1(
    val created_at: String,
    val entry_id: Int,
    val field1: String,
    val field2: String
)
data class Feed_2(
    val created_at: String,
    val entry_id: Int,
    val field1: String,
    val field2: String
)

