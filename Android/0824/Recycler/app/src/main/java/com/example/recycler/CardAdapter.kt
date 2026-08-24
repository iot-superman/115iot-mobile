package com.example.recycler

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView 的卡片清單轉接器。
 *
 * 此轉接器負責：
 * - 將 `dataList` 中的資料綁定到每個卡片項目。
 * - 顯示名稱文字與對應圖片。
 * - 處理卡片點擊事件（Toast）與名稱點擊事件（更新主畫面文字）。
 *
 * @property myContext 用於載入版面與顯示 UI 回饋的 `Context`。
 * @property dataList 卡片資料來源，每筆資料預期包含：
 * - `"name"`：顯示名稱（任意型別，會轉為字串）
 * - `"pic"`：圖片資源 ID（`Int`）
 */
class CardAdapter(
    private val myContext: Context,
    private var dataList: MutableList<MutableMap<String, Any>>
) : RecyclerView.Adapter<CardAdapter.MyViewHolder>() {

    /**
     * ViewHolder：快取單一卡片項目的 View 參照，避免重複查找提升效能。
     *
     * @param itemView 單一卡片的根 View（`R.layout.card_layout`）。
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /** 顯示卡片圖片的 ImageView。 */
        val imageViewPic: ImageView = itemView.findViewById(R.id.imageView)

        /** 顯示卡片名稱的 TextView。 */
        val textViewName: TextView = itemView.findViewById(R.id.textView)
    }

    /**
     * 建立新的 ViewHolder。
     *
     * @param parent RecyclerView 的父容器。
     * @param viewType 項目型別（此範例未區分多型別）。
     * @return 綁定 `card_layout` 的 `MyViewHolder`。
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(myContext).inflate(R.layout.card_layout, parent, false)
        return MyViewHolder(itemView)
    }

    /**
     * 將指定位置的資料綁定到 ViewHolder，並設定點擊事件。
     *
     * 行為說明：
     * - 點擊整張卡片：顯示 `Toast` 提示所選名稱。
     * - 點擊名稱文字：更新 Activity 內 `R.id.textView_data` 的文字內容。
     *
     * @param holder 目前要綁定資料的 ViewHolder。
     * @param position 目前資料在清單中的位置。
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]
        val name = data["name"].toString()
        val picId = data["pic"] as Int
        holder.textViewName.text = name
        holder.imageViewPic.setImageResource(picId)

        holder.itemView.setOnClickListener {
            Toast.makeText(myContext, "Select : $name", Toast.LENGTH_SHORT).show()
        }

        holder.textViewName.setOnClickListener {
            val mainTextView = (myContext as Activity).findViewById<TextView>(R.id.textView_data)
            mainTextView.text = "Select : $name"
        }
    }

    /**
     * 回傳目前清單項目數量。
     *
     * @return `dataList` 的大小。
     */
    override fun getItemCount(): Int {
        return dataList.size
    }
}