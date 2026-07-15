package com.example.layout3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.layout3.databinding.ActivityMainBinding

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

        // 監聽 Back Stack 變化，顯示/隱藏返回鍵（如果是 Toolbar 的話，這裡我們先用預設行為）
        // 如果想在 Fragment 中實現返回按鈕，我們可以在切換時加入 addToBackStack

        binding.button1.setOnClickListener {
            replaceFragment(FirstFragment(), "first")
        }

        binding.button2.setOnClickListener {
            replaceFragment(SecondFragment(), "second")
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(tag) // 加入返回棧，實現「返回介面」的效果
            .commit()
    }

    // 讓 Activity 的返回按鈕能正確處理 Fragment 的 Back Stack
    override fun onSupportNavigateUp(): Boolean {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            return true
        }
        return super.onSupportNavigateUp()
    }
}