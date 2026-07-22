package com.example.widget_5

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var textViewResult: TextView
    private lateinit var imageViewPc: ImageView
    private lateinit var imageButtonPaper: ImageButton
    private lateinit var imageButtonRock: ImageButton
    private lateinit var imageButtonCross: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageButtonCross = findViewById<ImageButton>(R.id.imageButton_cross)
        imageButtonRock = findViewById<ImageButton>(R.id.imageButton_rock)
        imageButtonPaper = findViewById<ImageButton>(R.id.imageButton_paper)

        imageViewPc = findViewById<ImageView>(R.id.imageView_pc)
        imageViewPc.visibility = View.INVISIBLE

        textViewResult = findViewById<TextView>(R.id.textView_result)

        imageButtonRock.setOnClickListener {
            updateButtonScales(it)
            val data = Math.random() * 3 + 1
            Log.d("main", "data rock= $data")
            val number = data.toInt()
            Log.d("main", "number rock= $number")
            imageViewPc.visibility = View.VISIBLE
            when (number) {
                1 -> {
                    imageViewPc.setImageResource(R.drawable.scissor)
                    textViewResult.setText(R.string.win)
                }
                2 -> {
                    imageViewPc.setImageResource(R.drawable.rock)
                    textViewResult.setText(R.string.draw)
                }
                3 -> {
                    imageViewPc.setImageResource(R.drawable.paper)
                    textViewResult.setText(R.string.lost)
                }
            }
        }

        imageButtonPaper.setOnClickListener {
            updateButtonScales(it)
            val data = Math.random() * 3 + 1
            Log.d("main", "data paper= $data")
            val number = data.toInt()
            Log.d("main", "number paper= $number")
            imageViewPc.visibility = View.VISIBLE
            when (number) {
                1 -> {
                    imageViewPc.setImageResource(R.drawable.scissor)
                    textViewResult.setText(R.string.lost)
                }
                2 -> {
                    imageViewPc.setImageResource(R.drawable.rock)
                    textViewResult.setText(R.string.win)
                }
                3 -> {
                    imageViewPc.setImageResource(R.drawable.paper)
                    textViewResult.setText(R.string.draw)
                }
            }
        }

        imageButtonCross.setOnClickListener {
            updateButtonScales(it)
            val data = Math.random() * 3 + 1
            Log.d("main", "data cross= $data")
            val number = data.toInt()
            Log.d("main", "number cross= $number")
            imageViewPc.visibility = View.VISIBLE
            when (number) {
                1 -> {
                    imageViewPc.setImageResource(R.drawable.scissor)
                    textViewResult.setText(R.string.draw)
                }
                2 -> {
                    imageViewPc.setImageResource(R.drawable.rock)
                    textViewResult.setText(R.string.lost)
                }
                3 -> {
                    imageViewPc.setImageResource(R.drawable.paper)
                    textViewResult.setText(R.string.win)
                }
            }
        }
//        val listener = MyImageButton()    //我自己寫法ＣＡＬＬ同一個
//        imageButtonRock.setOnClickListener(listener)
//        imageButtonPaper.setOnClickListener(listener)
//        imageButtonCross.setOnClickListener(listener)
    }

    private fun updateButtonScales(selectedButton: View) {
        val buttons = listOf(imageButtonRock, imageButtonPaper, imageButtonCross)
        buttons.forEach { button ->
            if (button == selectedButton) {
                button.animate().scaleX(1.5f).scaleY(1.5f).setDuration(200).start()
            } else {
                button.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start()
            }
        }
    }

    inner class MyImageButton : View.OnClickListener {
        override fun onClick(v: View?) {
            v?.let { updateButtonScales(it) }
//            val randomNumber = (Math.random()*3+1).toInt()  //老師版
            val randomNumber = (1..3).random()        //簡化版

            Log.d("main", "randomNumber=$randomNumber")

            when (randomNumber) {
                1 -> imageViewPc.setImageResource(R.drawable.scissor)
                2 -> imageViewPc.setImageResource(R.drawable.rock)
                3 -> imageViewPc.setImageResource(R.drawable.paper)
            }
            imageViewPc.visibility = View.VISIBLE

             //1 = 剪刀, 2 = 石頭, 3 = 布
             var win:Int = 0    //0:draw , 1:win , -1 :lose

            val userChoice = when (v?.id) {
                R.id.imageButton_cross -> 1
                R.id.imageButton_rock -> 2
                R.id.imageButton_paper -> 3
                else -> 0
            }
           if  (userChoice == randomNumber) {
             win = 0
           }
            else{
               win = -1
               when (userChoice) {
                   1 -> {
                       if (randomNumber == 3) {
                           win = 1
                       }
                   }

                   2 -> {
                       if (randomNumber == 1) {
                           win = 1
                       }
                   }

                   3 -> {
                       if (randomNumber == 2) {
                           win = 1
                       }
                   }
               }
           }

           when (win) {
               0 -> textViewResult.setText(R.string.draw)
               1 -> textViewResult.setText(R.string.win)
               -1 -> textViewResult.setText(R.string.lost)
           }
        }
    }
}
