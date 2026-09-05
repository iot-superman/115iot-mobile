package com.example.fragment_3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tabLayoutData = findViewById<TabLayout>(R.id.tabLayout_id)
        val viewPagerData = findViewById<ViewPager2>(R.id.viewPager_id)

        val adapter = MyViewAdapter(supportFragmentManager, lifecycle)
        viewPagerData.adapter = adapter

        // 加入切換動畫 (PageTransformer)
        viewPagerData.setPageTransformer { page, position ->
            page.apply {
                val absPos = Math.abs(position)
                alpha = 0.5f + (1 - absPos) * 0.5f
                scaleX = 0.85f + (1 - absPos) * 0.15f
                scaleY = 0.85f + (1 - absPos) * 0.15f
            }
        }

        TabLayoutMediator(tabLayoutData, viewPagerData) { tab, position ->
            tab.text = "Frag ${position + 1}"
        }.attach()
    }
}
