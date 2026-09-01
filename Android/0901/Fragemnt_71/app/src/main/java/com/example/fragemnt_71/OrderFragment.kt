package com.example.fragemnt_71

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class OrderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order, container, false)

        view.findViewById<Button>(R.id.button_cancel).setOnClickListener {
            activity?.finish()
        }

        view.findViewById<Button>(R.id.button_checkout).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CheckFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}