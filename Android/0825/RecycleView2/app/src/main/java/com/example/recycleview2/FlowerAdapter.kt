package com.example.recycleview2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FlowerAdapter(
    private val flowerList: List<Flower>,
    private val onItemClick: (Flower) -> Unit
) : RecyclerView.Adapter<FlowerAdapter.FlowerViewHolder>() {

    class FlowerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.textView_itemName)
        val picImageView: ImageView = view.findViewById(R.id.imageView_itemPic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return FlowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {
        val flower = flowerList[position]
        holder.nameTextView.text = flower.name
        holder.picImageView.setImageResource(flower.imageResourceId)
        holder.itemView.setOnClickListener {
            onItemClick(flower)
        }
    }

    override fun getItemCount(): Int = flowerList.size
}
