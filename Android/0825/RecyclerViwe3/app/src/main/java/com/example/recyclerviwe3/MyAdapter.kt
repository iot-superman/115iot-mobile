package com.example.recyclerviwe3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val myContext: Context,
    private val datalist: MutableList<MutableMap<String, Any>>,
    private val onQtyChanged: () -> Unit
) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPic: ImageView = itemView.findViewById(R.id.imageView_itemPic)
        val textViewName: TextView = itemView.findViewById(R.id.textView_itemName)
        val textViewPrice: TextView = itemView.findViewById(R.id.textView_itemPrice)
        val editTextQty: EditText = itemView.findViewById(R.id.editText_itemNumber)
        val imageViewMinus: ImageView = itemView.findViewById(R.id.imageView_itemMinus)
        val imageViewPlus: ImageView = itemView.findViewById(R.id.imageView_itemPlus)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyAdapter.ViewHolder {
        val itemView = LayoutInflater.from(myContext).inflate(R.layout.item_layout, parent, false)
        val viewHolder = ViewHolder(itemView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        val data = datalist.get(position)
        val name = data["name"].toString()
        val price = data["price"] as Int
        val picId = data["pic"] as Int

        holder.textViewName.text = name
        holder.textViewPrice.text = price.toString()
        holder.imageViewPic.setImageResource(picId)
        holder.editTextQty.setText(data["qty"].toString())

        // 點擊 + 號增加數量
        holder.imageViewPlus.setOnClickListener {
            var currentQty = data["qty"] as Int
            currentQty++
            data["qty"] = currentQty
            holder.editTextQty.setText(currentQty.toString())
            onQtyChanged()
        }

        // 點擊 - 號減少數量 (不能低於 0)
        holder.imageViewMinus.setOnClickListener {
            var currentQty = data["qty"] as Int
            if (currentQty > 0) {
                currentQty--
                data["qty"] = currentQty
                holder.editTextQty.setText(currentQty.toString())
                onQtyChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    fun returnData(): MutableList<MutableMap<String, Any>> {
        return datalist
    }
}