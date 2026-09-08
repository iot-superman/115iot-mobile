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

class Fragment_2 : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var textViewTitle: TextView
    private lateinit var textViewData: TextView
    private lateinit var buttonChange: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("param1")
            param2 = it.getString("param2")
            Log.d("main", "frag2 p1 = $param1")
            Log.d("main", "frag2 p2 = $param2")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragView = inflater.inflate(R.layout.fragment2, container, false)
        textViewTitle = fragView.findViewById<TextView>(R.id.tvFrag2_Title)
        textViewData = fragView.findViewById<TextView>(R.id.textView_Frag2_data)
        buttonChange = fragView.findViewById<Button>(R.id.button_Frag2)
        
        textViewTitle.text = param1
        textViewData.text = param2

        val editTextInput = activity?.findViewById<EditText>(R.id.editText_mainInput)
        
        buttonChange.setOnClickListener {
            val data = if (editTextInput?.length() == 0) {
                "no input data in from main"
            } else {
                editTextInput?.text.toString()
            }

            textViewData.text = data
        }

        return fragView
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