package com.example.fragment_2

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class Fragment_3 : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var textViewTitle: TextView
    private lateinit var buttonChange: Button
    private lateinit var boutonReturn: Button
    private lateinit var editTextInputSelf: EditText
    
    private var listener: FragmentDataListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentDataListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement FragmentDataListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("param1")
            param2 = it.getString("param2")
            Log.d("main", "frag3 p1 = $param1")
            Log.d("main", "frag3 p2 = $param2")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragView = inflater.inflate(R.layout.fragment3, container, false)
        textViewTitle = fragView.findViewById<TextView>(R.id.tvFrag3)
        buttonChange = fragView.findViewById<Button>(R.id.button)
        boutonReturn = fragView.findViewById<Button>(R.id.button_return)
        editTextInputSelf = fragView.findViewById<EditText>(R.id.editTextText)
        
        textViewTitle.text = param1

        val editTextInputMain = activity?.findViewById<EditText>(R.id.editText_mainInput)

        buttonChange.setOnClickListener {
            val data = if (editTextInputMain?.length() == 0) {
                "no input data in from main"
            } else {
                editTextInputMain?.text.toString()
            }

            textViewTitle.text = data
        }

        boutonReturn.setOnClickListener {
            val returnData = editTextInputSelf.text.toString()
            listener?.onDataReceived(returnData)
        }

        return fragView
    }

    companion object {
        fun newInstance(param1: String, param2: String): Fragment_3 {
            val fragment = Fragment_3()
            val args = Bundle()
            args.putString("param1", param1)
            args.putString("param2", param2)
            fragment.arguments = args
            return fragment
        }
    }
}