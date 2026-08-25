package com.example.recycleview2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val myContext: Context, private val dataList: MutableList<MutableMap<String, Any>>,
        val adapterClick: (MutableMap<String, Any>) -> Unit) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textView_itemName)
        val imageViewPic: ImageView = itemView.findViewById(R.id.imageView_itemPic)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(myContext).inflate(R.layout.item_layout, parent, false)
        val viewHolder = MyViewHolder(itemView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        val data = dataList[position]
        val name = data.get("name").toString()
        val picId = data.get("pic") as Int
        holder.textViewName.text = name
        holder.imageViewPic.setImageResource(picId)

        holder.itemView.setOnClickListener {
            Log.d("main", "it")
            adapterClick(data)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
