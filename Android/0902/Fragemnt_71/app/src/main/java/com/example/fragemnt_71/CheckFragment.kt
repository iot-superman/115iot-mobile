package com.example.fragemnt_71

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class CheckFragment : Fragment() {

    private lateinit var textViewCheckList: TextView
    private lateinit var buttonCancel: Button
    private lateinit var buttonConfirm: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_check, container, false)
        
        textViewCheckList = fragView.findViewById(R.id.textView3)
        buttonCancel = fragView.findViewById(R.id.button_cancel_check)
        buttonConfirm = fragView.findViewById(R.id.button_confirm)



        return fragView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val checkoutList = arguments?.getStringArray("checkdata")
        
        if (checkoutList != null) {
            val sb = StringBuilder()
            for (item in checkoutList) {
                sb.append(item)
            }
            textViewCheckList.text = "Your order:\n"
            textViewCheckList.append(sb.toString())
        }
        
        buttonCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        buttonConfirm.setOnClickListener {
            activity?.finish()
        }
    }

    /**
     * `CheckFragment` 的工廠區塊，集中提供建立 Fragment 的入口。
     */
    companion object {
        /**
         * 建立新的 `CheckFragment`，並透過 arguments 傳入待確認的清單資料。
         *
         * @param checkList 待顯示於確認頁面的字串清單。
         * @return 已附帶 `checkdata` arguments 的 `CheckFragment` 實例。
         */
        @JvmStatic
        fun newInstance(checkList: MutableList<String>) =
            CheckFragment().apply {
                arguments = Bundle().apply {
                    val dataArray = checkList.toTypedArray()
                    putStringArray("checkdata", dataArray)
                }
            }
    }
}
