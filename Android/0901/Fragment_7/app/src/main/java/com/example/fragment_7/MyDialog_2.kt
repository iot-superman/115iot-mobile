package com.example.fragment_7

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class MyDialog_2(val listen: (data: String) -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dlgView = requireActivity().layoutInflater.inflate(R.layout.dialog_layout, null)
        val editTextInput = dlgView.findViewById<EditText>(R.id.editTextText_dlginput)

        val builder = AlertDialog.Builder(requireContext()).apply {
            setTitle("Fragment Dialog 2")
            setView(dlgView)
            setPositiveButton("OK") { dialog, _ ->
                val data = editTextInput.text.toString()
                listen(data)
                dialog.dismiss()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        }

        return builder.create()
    }
}
