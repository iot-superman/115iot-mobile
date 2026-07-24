package com.example.no6_0722_ex01

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var imageButton1: ImageButton
    private lateinit var imageButton2: ImageButton
    private lateinit var imageButton3: ImageButton
    private lateinit var imageButton4: ImageButton
    private lateinit var imageButton5: ImageButton
    private lateinit var imageButton6: ImageButton
    private lateinit var textView: TextView
    private lateinit var gridLayout: GridLayout
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 初始化元件
        gridLayout = findViewById(R.id.gridLayout)
        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView)

        imageButton1 = findViewById(R.id.imageButton1)
        imageButton2 = findViewById(R.id.imageButton2)
        imageButton3 = findViewById(R.id.imageButton3)
        imageButton4 = findViewById(R.id.imageButton4)
        imageButton5 = findViewById(R.id.imageButton5)
        imageButton6 = findViewById(R.id.imageButton6)

        // 建立內部類別的實例
        val listener = ClickImageButton()

        // 所有的按鈕都複用同一個 listener 實例
        imageButton1.setOnClickListener(listener)
        imageButton2.setOnClickListener(listener)
        imageButton3.setOnClickListener(listener)
        imageButton4.setOnClickListener(listener)
        imageButton5.setOnClickListener(listener)
        imageButton6.setOnClickListener(listener)
    }

    // 將內部類別定義在 MainActivity 內，並實作 View.OnClickListener
    inner class ClickImageButton : View.OnClickListener {
        override fun onClick(v: View?) {
            // 點擊縮放動畫：放大到 1.25x 後縮回 1x
            // 增加 translationZ 確保放大時會在最上層（破框並蓋在其他按鈕上方）
            v?.translationZ = 10f
            v?.animate()
                ?.scaleX(1.25f)
                ?.scaleY(1.25f)
                ?.setDuration(100)
                ?.withEndAction {
                    v.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .withEndAction {
                            v.translationZ = 0f // 動畫結束後恢復 Z 軸高度
                        }
                        .start()
                }
                ?.start()

            when (v?.id) {
                R.id.imageButton1 -> { imageView.setImageResource(R.drawable.img1)
                    textView.text = "波斯菊"
                }
                R.id.imageButton2 -> { imageView.setImageResource(R.drawable.img2)
                    textView.text = "玫瑰"
                }
                R.id.imageButton3 -> { imageView.setImageResource(R.drawable.img3)
                    textView.text = "牡丹"
                }
                R.id.imageButton4 -> { imageView.setImageResource(R.drawable.img4)
                    textView.text = "鈴蘭"
                }
                R.id.imageButton5 -> { imageView.setImageResource(R.drawable.img5)
                    textView.text = "水仙"
                }
                R.id.imageButton6 -> { imageView.setImageResource(R.drawable.img6)
                    textView.text = "海芋"
                }
            }
        }
    }
}
