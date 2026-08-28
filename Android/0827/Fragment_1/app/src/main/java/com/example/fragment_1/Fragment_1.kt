package com.example.fragment_1

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_1 : Fragment() {
    private var imgFlag: Boolean = true
    private lateinit var buttonData: Button
    private lateinit var editTextInput: EditText
    private lateinit var buttonPic: Button
    private lateinit var textViewName: TextView
    private lateinit var imageViewPic: ImageView

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            Log.d("main", "onCreate-F")
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("main", "onCreateView-F")
        val fragView = inflater.inflate(R.layout.fragment_1, container, false)
        imageViewPic = fragView.findViewById<ImageView>(R.id.imageView_fragPic)
        textViewName = fragView.findViewById<TextView>(R.id.textView_fragName)
        buttonPic = fragView.findViewById<Button>(R.id.button_frag)
        editTextInput  = fragView.findViewById<EditText>(R.id.editTextText_frag)
        buttonData = fragView.findViewById<Button>(R.id.button_FragData)

        imgFlag = true
        buttonPic.setOnClickListener {
            if(imgFlag){
                textViewName.text = "Flower 2"
                imageViewPic.setImageResource(R.drawable.flower2)
                imgFlag = false
            }else{
                textViewName.text = "Flower 1"
                imageViewPic.setImageResource(R.drawable.flower1)
                imgFlag = true
            }
        }

        return fragView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment_1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("main", "onAttach-F")
    }
    override fun onStart() {
        super.onStart()
        Log.d("main", "onStart-F")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("main", "onViewCreated-F")
    }

    override fun onResume() {
        super.onResume()
        Log.d("main", "onResume-F")
        val textViewMain = activity?.findViewById<TextView>(R.id.textView_mainData)
        buttonData.setOnClickListener {
            val input = editTextInput.text.toString()
            val data = if (input.isEmpty())
                "no input data in fragment"
            else
                input
            textViewMain?.text = data
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("main", "onStop-F")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("main", "onDestroyView-F")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("main", "onDestroy-F")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("main", "onDetach-F")
    }
}