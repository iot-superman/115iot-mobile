package com.example.listview6

import android.os.Bundle
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val flowerImages = intArrayOf(
        R.drawable.flower1, R.drawable.flower2, R.drawable.flower3,
        R.drawable.flower4, R.drawable.flower5, R.drawable.flower6
    )

    private val flowerNames = arrayOf(
        "Flower 1", "Flower 2", "Flower 3",
        "Flower 4", "Flower 5", "Flower 6"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gridView = findViewById<GridView>(R.id.gridView_id)
        val imageView = findViewById<ImageView>(R.id.ImageView_pic)

        gridView.adapter = ImageAdapter()

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            imageView.setImageResource(flowerImages[position])
        }
    }

    inner class ImageAdapter : BaseAdapter() {
        override fun getCount(): Int = flowerImages.size

        override fun getItem(position: Int): Any = flowerImages[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val holder: ViewHolder

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.item_layout, parent, false)
                holder = ViewHolder()
                holder.imageView = view.findViewById(R.id.imageView_itemPic)
                holder.textView = view.findViewById(R.id.textView_itemName)
                view.tag = holder
            } else {
                view = convertView
                holder = view.tag as ViewHolder
            }

            holder.imageView.setImageResource(flowerImages[position])
            holder.textView.text = flowerNames[position]

            return view
        }
    }

    private class ViewHolder {
        lateinit var imageView: ImageView
        lateinit var textView: android.widget.TextView
    }
}