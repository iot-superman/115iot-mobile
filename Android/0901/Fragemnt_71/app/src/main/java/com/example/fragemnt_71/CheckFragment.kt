package com.example.fragemnt_71

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class CheckFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_check, container, false)

        view.findViewById<Button>(R.id.button_cancel_check).setOnClickListener {
            // Return to OrderFragment
            parentFragmentManager.popBackStack()
        }

        view.findViewById<Button>(R.id.button_confirm).setOnClickListener {
            // Confirm order and return to MainActivity
            activity?.finish()
        }

        return view
    }
}