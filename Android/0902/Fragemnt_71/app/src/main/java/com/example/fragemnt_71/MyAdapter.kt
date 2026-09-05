package com.example.fragemnt_71

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val myContext: Context,
    private val data: MutableList<MutableMap<String, Any>>
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgItem: ImageView = view.findViewById(R.id.imageView_item)
        val tvName: TextView = view.findViewById(R.id.textView_name)
        val tvPrice: TextView = view.findViewById(R.id.textView_price)
        val tvQty: TextView = view.findViewById(R.id.textView_qty)
        val btnPlus: ImageButton = view.findViewById(R.id.btn_plus)
        val btnMinus: ImageButton = view.findViewById(R.id.btn_minus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(myContext).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.tvName.text = item["name"].toString()
        holder.tvPrice.text = "$ ${item["price"]}"
        holder.imgItem.setImageResource(item["pic"] as Int)
        holder.tvQty.text = item["qty"].toString()

        holder.btnPlus.setOnClickListener {
            var qty = item["qty"] as Int
            qty++
            item["qty"] = qty
            holder.tvQty.text = qty.toString()
        }

        holder.btnMinus.setOnClickListener {
            var qty = item["qty"] as Int
            if (qty > 0) {
                qty--
                item["qty"] = qty
                holder.tvQty.text = qty.toString()
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun returnData(): MutableList<MutableMap<String, Any>> {
        return data
    }
}
