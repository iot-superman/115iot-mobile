package com.example.listview7

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class SpinnerArrayAdapter(val myContext: Context, val listData: List<SpinnerItem>) :
    ArrayAdapter<SpinnerItem>(myContext, 0, listData) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 為避免 collapsed view 與 dropdown view 混用導致的圖片顯示異常，這裡重新 inflate
        val view = LayoutInflater.from(myContext).inflate(R.layout.spinner_layout, parent, false)
        return bindData(view, position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(myContext).inflate(R.layout.spinner_dropdown_item, parent, false)
        return bindData(view, position)
    }

    private fun bindData(view: View, position: Int): View {
        val item = listData[position]
        val imageView = view.findViewById<ImageView>(R.id.imgItem)
        val textView = view.findViewById<TextView>(R.id.txtItem)

        if (item.image != 0) {
            imageView.setImageResource(item.image)
        } else {
            // 如果圖片 ID 為 0，顯示預設圖片
            imageView.setImageResource(R.drawable.icecream)
        }
        textView.text = item.name

        return view
    }
}
