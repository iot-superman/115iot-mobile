package com.example.fragment_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class Fragment_2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment2, container, false)
        val textView = view.findViewById<TextView>(R.id.tvFrag2)
        arguments?.getString("param1")?.let {
            textView.text = it
        }
        return view
    }

    companion object {
        fun newInstance(param1: String, param2: String): Fragment_2 {
            val fragment = Fragment_2()
            val args = Bundle()
            args.putString("param1", param1)
            args.putString("param2", param2)
            fragment.arguments = args
            return fragment
        }
    }
}