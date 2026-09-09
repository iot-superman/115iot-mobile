package com.example.fragment_2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class Fragment_1 : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var textViewTitle: TextView
    private lateinit var buttonChange: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("param1")
            param2 = it.getString("param2")
            Log.d("main", "frag1 p1 = $param1")
            Log.d("main", "frag1 p2 = $param2")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragView = inflater.inflate(R.layout.fragment1, container, false)
        textViewTitle = fragView.findViewById<TextView>(R.id.tvFrag1_title)
        buttonChange = fragView.findViewById<Button>(R.id.button_Frag1)
        
        textViewTitle.text = param1

        val editTextInput = activity?.findViewById<EditText>(R.id.editText_mainInput)

        buttonChange.setOnClickListener {
            val data = if (editTextInput?.length() == 0) {
                "no input data in from main"
            } else {
                editTextInput?.text.toString()
            }

            textViewTitle.text = data
        }

        return fragView
    }

    companion object {
        fun newInstance(param1: String, param2: String): Fragment_1 {
            val fragment = Fragment_1()
            val bundle = Bundle()
            bundle.putString("param1", param1)
            bundle.putString("param2", param2)
            fragment.arguments = bundle
            return fragment
        }
    }
}