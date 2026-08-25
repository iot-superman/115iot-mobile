package com.example.recyclerviwe3

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var textViewCheckout: TextView
    private lateinit var buttonCheckout: Button
    private lateinit var buttonCancel: Button
    private lateinit var recyclerViewData: RecyclerView

    private lateinit var datalist: MutableList<MutableMap<String, Any>>
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerViewData = findViewById(R.id.recyclerView_id)
        buttonCancel = findViewById(R.id.button_cancel)
        buttonCheckout = findViewById(R.id.button_checkout)
        textViewCheckout = findViewById(R.id.textView_checkout)
        textViewCheckout.text = ""

        val nameArray = resources.getStringArray(R.array.name)
        val priceArray = resources.getIntArray(R.array.price)
        val picArray = resources.obtainTypedArray(R.array.picture)

        datalist = mutableListOf<MutableMap<String, Any>>()
        for (i in 0 .. nameArray.size - 1) {
            val data = mutableMapOf<String, Any>()
            data.put("name", nameArray[i])
            data.put("price", priceArray[i])
            data.put("pic", picArray.getResourceId(i, R.drawable.blacktea))
            data.put("qty", 0)

            datalist.add(data)
        }
        picArray.recycle()

        Log.d("main", "datalist = $datalist")

        val layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        recyclerViewData.layoutManager = layoutManager

        adapter = MyAdapter(myContext = this@MainActivity, datalist) {
        }
        recyclerViewData.adapter = adapter

        buttonCancel.setOnClickListener {
            for (data in datalist) {
                data["qty"] = 0
            }
            adapter.notifyDataSetChanged()
            textViewCheckout.text = ""
        }

        buttonCheckout.setOnClickListener {
            val latestDataList = adapter.returnData()
            textViewCheckout.text = "Your order is :\n"
            var sum = 0

            for (data in latestDataList) {
                val name = data["name"].toString()
                val price = data["price"] as Int
                val qty = data["qty"] as Int

                if (qty > 0) {
                    sum += qty * price
                    textViewCheckout.append("$name : $price x $qty = $${qty * price}\n")
                }
            }

            textViewCheckout.append("\nThe total fee = $$sum")
        }
    }
}