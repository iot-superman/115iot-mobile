package com.example.fragment_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class Fragment_1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment1, container, false)
        val textView = view.findViewById<TextView>(R.id.tvFrag1)
        arguments?.getString("param1")?.let {
            textView.text = it
        }
        return view
    }

    companion object {
        fun newInstance(param1: String, param2: String): Fragment_1 {
            val fragment = Fragment_1()
            val args = Bundle()
            args.putString("param1", param1)
            args.putString("param2", param2)
            fragment.arguments = args
            return fragment
        }
    }
}