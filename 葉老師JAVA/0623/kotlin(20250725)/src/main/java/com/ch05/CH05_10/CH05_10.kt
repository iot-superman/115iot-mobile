package com.ch05.CH05_10
// 補充
// const val 類別定義
// 只允許在 top-level 或 object
// 只支援 基本型別（如 String, Int）
// 適用於常數宣告（如標準常數定義）
object R {
    object drawable {
        const val cat = 0x7f0c0071
        const val flower = 0x7f0c0072
        const val hippo = 0x7f0c0073
        const val monkey = 0x7f0c0074
        const val mushroom = 0x7f0c0075
        const val panda = 0x7f0c0076
        const val rabbit = 0x7f0c0077
        const val raccoon = 0x7f0c0078
    }

    object id {
        const val index_button1 = 0x3f0c0071
        const val index_button2 = 0x3f0c0072
    }
}

val image = intArrayOf(
    R.drawable.cat, R.drawable.flower, R.drawable.hippo,
    R.drawable.monkey, R.drawable.mushroom, R.drawable.panda,
    R.drawable.rabbit, R.drawable.raccoon
)

fun main() {
    findViewById(R.drawable.cat)
    findViewById(R.drawable.raccoon)
    findViewById(R.id.index_button1)

    // Kotlin 不用 new，且 drawable 是 object，不能建立實例
    // 直接使用常數即可
    findViewById(R.drawable.raccoon)
}

fun findViewById(element: Int) {
    println("element address=$element")
}
