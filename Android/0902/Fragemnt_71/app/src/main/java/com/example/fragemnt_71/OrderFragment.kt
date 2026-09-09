package com.example.fragemnt_71

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderFragment : Fragment() {

    private lateinit var dataList: MutableList<MutableMap<String, Any>>
    private lateinit var recyclerViewData: RecyclerView
    private lateinit var buttonCancel: Button
    private lateinit var buttonCheckout: Button
    private lateinit var textViewTotal: TextView
    private lateinit var adapter: MyAdapter
    private lateinit var checkoutList: MutableList<String>
    private lateinit var qytArray: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nameArray = resources.getStringArray(R.array.nameArray)
        val priceArray = resources.getStringArray(R.array.priceArray)
        val picArray = resources.obtainTypedArray(R.array.picArray)
        
        qytArray = IntArray(nameArray.size) { 0 }
        
        dataList = mutableListOf<MutableMap<String, Any>>()
        for (i in nameArray.indices) {
            val data = mutableMapOf<String, Any>()
            data.put("name", nameArray[i])
            data.put("price", priceArray[i].toInt())
            data.put("pic", picArray.getResourceId(i, R.drawable.ic_launcher_background))
            data.put("qty", 0)
            dataList.add(data)
        }
        picArray.recycle()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_order, container, false)
        
        recyclerViewData = fragView.findViewById(R.id.recyclerView_id)
        buttonCancel = fragView.findViewById(R.id.button_cancel)
        buttonCheckout = fragView.findViewById(R.id.button_checkout)
        textViewTotal = fragView.findViewById(R.id.textView_total)
        
        return fragView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewData.layoutManager = layoutManager
        
        adapter = MyAdapter(requireContext(), dataList)
        recyclerViewData.adapter = adapter

        checkoutList = mutableListOf<String>()

        buttonCheckout.setOnClickListener {
            val allData = adapter.returnData()
            var sum = 0
            checkoutList.clear()
            for (data in allData) {
                val name = data["name"].toString()
                val price = data["price"] as Int
                val qty = data["qty"] as Int
                if (qty > 0) {
                    sum += price * qty
                    checkoutList.add("$name : $price x $qty = $$${price*qty}\n")
                }
            }
            checkoutList.add("\nThe total fee : $$sum")
            Log.d("main", "check list = $checkoutList")

            if (sum > 0) {
                val checkFrag = CheckFragment.newInstance(checkList = checkoutList)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout_order, checkFrag)
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Please select items!", Toast.LENGTH_SHORT).show()
            }
        }

        buttonCancel.setOnClickListener {
            for (i in 0 .. qytArray.size-1) {
                qytArray[i] = 0
                val data = dataList[i]
                data["qty"] = 0
                dataList.set(i, data)
            }
            adapter.notifyDataSetChanged()
            checkoutList.clear()
        }
    }
}
