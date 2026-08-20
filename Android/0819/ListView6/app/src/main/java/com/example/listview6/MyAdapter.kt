package com.example.listview6

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * 自訂配接器 (Adapter)，用於將資料清單綁定到自訂的佈局 (item_layout) 上。
 */
class MyAdapter(
    private val myContext: Context,
    private val listData: MutableList<MutableMap<String, Any>>
) : BaseAdapter() {

    /**
     * 回傳資料總筆數。
     */
    override fun getCount(): Int {
        return listData.size
    }

    /**
     * 取得指定位置的資料物件。
     */
    override fun getItem(position: Int): Any? {
        return listData[position]
    }

    /**
     * 取得指定位置的項目 ID。
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * 取得項目的視圖 (View)。使用 ViewHolder 模式來優化效能，避免重複呼叫 findViewById。
     */

    /*
    在 Android 開發中，holder（也就是 ViewHolder 模式）的主要目的是**「效能優化」**，特別是為了讓列表（ListView 或 GridView）在滾動時更加順暢。
以下是它的具體作用和原理：
1. 減少 findViewById 的次數（最主要的原因）
在沒有 holder 的情況下，每次 getView 被呼叫時（也就是每一列出現在螢幕上時），程式都必須執行 view.findViewById() 來尋找元件。
•
findViewById 是一個比較「耗時」的操作，因為它必須遍歷整個 XML 佈局樹狀結構。
•
如果你的列表有幾百筆資料，滑動時就會不斷重複執行這些操作，導致介面卡頓。
使用 holder 後： 我們只在第一次建立 View 時執行 findViewById，然後把這些元件的引用（Reference）存進 holder 物件裡。下次這筆資料要顯示時，直接從 holder 取出即可，不需要重新尋找。
2. 配合 View 的「重用機制」(Recycling)
Android 的列表非常聰明，當一個項目滑出螢幕後，它不會被銷毀，而是會被放進一個「回收池」中。當下方有新的項目要進入螢幕時，系統會把剛才那個舊的 View 給你重複使用（這就是 convertView）。
•
convertView == null：表示這是新創立的項目。我們會建立 holder，找好元件，然後用 view.tag = holder 把它像標籤一樣貼在 View 上。
•
convertView != null：表示這是回收回來的舊 View。我們直接透過 view.tag 拿回之前存好的 holder，馬上就能幫裡面的元件設定新的內容。
生活化的比喻：
想像你是一個餐廳服務生：
•
沒用 holder：每次客人點餐，你都要跑回廚房問：「叉子在哪？湯匙在哪？盤子在哪？」問完才拿給客人。如果客人很多，你就跑死了。
•
有用 holder：第一次客人來，你問清楚位置後，把叉子、湯匙、盤子全部裝在一個**「托盤」(Holder)** 上。下次有新客人（重用桌子）時，你直接端著已經準備好的「托盤」過去就好，不用再回廚房到處找。
總結優點：
1.
省電、省 CPU：減少運算次數。
2.
滑動順暢：避免列表在快速滾動時產生掉幀（Lag）。
3.
程式碼結構清晰：清楚地將「尋找元件」與「設定資料」分開。
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View
        val holder: ViewHolder   //chatgpt.com/s/m_6a840d1c83608191801d717fcae788aa

        if (convertView == null) {
            // 如果沒有可重用的視圖，則載入新的佈局
            view = LayoutInflater.from(myContext).inflate(R.layout.item_layout, parent, false)
            // 建立 ViewHolder 並尋找視圖中的元件
            holder = ViewHolder(
                itemImageView = view.findViewById(R.id.imageView_itemPic),
                itemTextView = view.findViewById(R.id.textView_itemName)
            )
            // 將 ViewHolder 儲存在視圖的 tag 中
            view.tag = holder
        } else {
            // 如果有可重用的視圖，則直接從 tag 中取得 ViewHolder
            view = convertView
            holder = view.tag as ViewHolder
        }

        // 取得當前位置的資料
        val item = listData[position]
        
        // 將資料綁定到元件上
        holder.itemTextView.text = item["name"].toString()
        holder.itemImageView.setImageResource(item["pic"] as Int)

        return view
    }

    /**
     * ViewHolder 類別，用於快取佈局中的元件。
     */
    private class ViewHolder(
        val itemImageView: ImageView,
        val itemTextView: TextView
    )
}
