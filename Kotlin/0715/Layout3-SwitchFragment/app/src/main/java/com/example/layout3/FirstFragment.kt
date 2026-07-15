package com.example.layout3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        
        // 實作返回按鈕的功能
        view.findViewById<Button>(R.id.buttonBack).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        
        return view
    }
}