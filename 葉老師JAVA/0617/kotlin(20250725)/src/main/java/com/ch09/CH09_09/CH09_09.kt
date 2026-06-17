package com.ch09.CH09_09

object R {
    object drawable {
        const val cat = 1
        const val flower = 2
        const val hippo = 3
        const val monkey = 4
        const val mushroom = 5
        const val panda = 6
        const val rabbit = 7
        const val raccoon = 8
    }
}

fun main() {
    val image = arrayOf(
        R.drawable.cat, R.drawable.flower, R.drawable.hippo,
        R.drawable.monkey, R.drawable.mushroom, R.drawable.panda,
        R.drawable.rabbit, R.drawable.raccoon
    )

    val imgText = arrayOf(
        "cat", "flower", "hippo", "monkey", "mushroom", "panda", "rabbit", "raccoon"
    )

    val items1 = mutableListOf<Map<String, Any>>()
    // for
    for (i in image.indices) {
        val item = mapOf(
            "image" to image[i],
            "text" to imgText[i]
        )
        items1.add(item)
    }

    // forEach
    val items2 = mutableListOf<Map<String, Any>>()
    image.zip(imgText).forEach { (img, txt) ->
        val item = mapOf(
            "image" to img,
            "text" to txt
        )
        items2.add(item)
    }
    // lambda
    val items3 = image.zip(imgText).map { (img, txt) ->
        mapOf("image" to img, "text" to txt)
    }

    println(".................1.................")
    println(items1) // 檢查內容
    println(items2) // 檢查內容
    println(items3) // 檢查內容

    //
    println(".................2.................")
    for (item in items3) {
        for ((key, value) in item) {
            print("$key: $value ")
        }
        println()
    }
    println(".................3.................")
    // lambda
    items3.forEach { item ->
        item.forEach { (key, value) ->
            print("$key: $value ")
        }
        println()
    }
    println(".................4.................")
    items3.forEach { it.forEach { (k, v) -> print("$k: $v ") }; println() }
}