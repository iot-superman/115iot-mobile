package com.example.menu7

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navView: NavigationView
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var myDrawer: DrawerLayout
    private lateinit var textViewData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.drawer_layout)
        
        myDrawer = findViewById(R.id.drawerlayout_id)
        navView = findViewById(R.id.navigationView)
        textViewData = findViewById(R.id.textView1)
        
        ViewCompat.setOnApplyWindowInsetsListener(myDrawer) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        drawerToggle = ActionBarDrawerToggle(
            this, 
            myDrawer, 
            R.string.open, 
            R.string.close
        )

        myDrawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // 解決圖示沒顯示原色的問題（禁用預設著色）
        navView.itemIconTintList = null

        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_item1 -> {
                    textViewData.text = "Item 1"
                }
                R.id.nav_item2 -> {
                    textViewData.text = "Item 2"
                }
                R.id.nav_item3 -> {
                    textViewData.text = "Item 3"
                }
            }
            myDrawer.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}