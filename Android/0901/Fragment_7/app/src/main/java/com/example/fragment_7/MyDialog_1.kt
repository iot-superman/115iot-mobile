package com.example.fragment_7

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MyDialog_1: DialogFragment() {
    
    
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireActivity())
        builder.apply {
            setTitle("Fragment Dialog 1")
            setMessage("This is Fragment Dialog")
        }

        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        return builder.create()
    }
    
}