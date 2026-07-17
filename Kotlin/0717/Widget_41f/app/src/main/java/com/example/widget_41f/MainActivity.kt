package com.example.widget_41f

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.example.widget_41f.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tv1.text = "1234"
        binding.imageButton1.setOnClickListener (MyImgButton())
        binding.imageButton2.setOnClickListener (MyImgButton())
        binding.imageButton3.setOnClickListener (MyImgButton())
        binding.imageButton4.setOnClickListener (MyImgButton())
        binding.imageButton5.setOnClickListener (MyImgButton())
        binding.imageButton6.setOnClickListener (MyImgButton())

    }

   inner class MyImgButton : View.OnClickListener {
        override fun onClick(p0: View?) {
          when(p0?.id){
              R.id.imageButton1 -> binding.tv1.text = "this is flower 1"
              R.id.imageButton2 -> binding.tv2.text = "this is flower 2"
              R.id.imageButton3 -> binding.tv3.text = "this is flower 3"
              R.id.imageButton4 -> binding.tv1.text = "this is flower 4"
              R.id.imageButton5 -> binding.tv2.text = "this is flower 5"
              R.id.imageButton6 -> binding.tv3.text = "this is flower 6"
          }

        }

    }


}



