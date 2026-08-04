package com.example.menu_1

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var imgView: ImageView
    private lateinit var textViewData: TextView
    private var picFlag = true
    private var colorFlag = true

    companion object {
        const val ChangPicture = Menu.FIRST
        const val ChangeColor = Menu.FIRST + 1
        const val ChangeText = Menu.FIRST + 2
        const val Text1 = Menu.FIRST + 3
        const val Text2 = Menu.FIRST + 4
        const val About = Menu.FIRST + 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textViewData = findViewById<TextView>(R.id.textView)
        imgView = findViewById<ImageView>(R.id.imageView)
        imgView.tag = "flower1"
        textViewData.tag = "color1"

    }// end of onCreate()`



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menu?.add(1, ChangPicture, Menu.NONE, "Change picture")
        menu?.add(1, ChangeColor, Menu.NONE, "Change color")
        val sunMenu = menu?.addSubMenu(1, ChangeText, Menu.NONE, "Change text")
        sunMenu?.add(1, Text1, Menu.NONE, "Text 1")
        sunMenu?.add(1, Text2, Menu.NONE, "Text 2")
        menu?.add(1, About, Menu.NONE, "About")
        return true
    }

    // onOptionItemSelected is called when a menu item is selected.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            ChangPicture -> {
                Toast.makeText(this, "Change picture clicked", Toast.LENGTH_SHORT).show()
                // Teacher Version:
                if (picFlag) {
                    imgView.setImageResource(R.drawable.flower2)
                    picFlag = false
                } else {
                    imgView.setImageResource(R.drawable.flower1)
                    picFlag = true
                }
                true
            }
            ChangeColor -> {
                Toast.makeText(this, "Change color clicked", Toast.LENGTH_SHORT).show()
                // Teacher Version:
                if (colorFlag) {
                    textViewData.setTextColor(0x7FFF88FF.toInt())
                    colorFlag = false
                } else {
                    textViewData.setTextColor(0x7F2196F3.toInt())
                    colorFlag = true
                }

                // MY Version:
                /*
                if (textViewData.tag == "color1") {
                    textViewData.setTextColor(Color.parseColor("#FF0000"))
                    textViewData.tag = "color2"
                } else {
                    textViewData.setTextColor(Color.parseColor("#00FF00"))
                    textViewData.tag = "color1"
                }
                */
                true
            }
            Text1 -> {
                textViewData.text = "There are lots of flowers"
                true
            }
            Text2 -> {
                textViewData.text = "This is a pretty flower"
                true
            }
            About -> {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("About option menu")
                builder.setIcon(android.R.drawable.ic_dialog_info)
                builder.setMessage("This is alert dialog")
                builder.setPositiveButton("ok", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        textViewData.text = "About  dialog"
                    }
                })
                builder.setNegativeButton("cancel", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        textViewData.text = "Cancel  dialog"
                        dialog?.dismiss()
                    }
                })
                builder.show()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }
}