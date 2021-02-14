package com.example.myscheduler

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmDialog(private val message: String,
                    private val okLabel: String,
                    private val okSelected: () -> Unit,
                    private val cancelLabel: String,
                    private val cancelSelected: () -> Unit)
    : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(message)
        builder.apply {
            setPositiveButton(okLabel, DialogInterface.OnClickListener{
                dialog, which ->
                okSelected()
            })
        }
        builder.apply {
            setNegativeButton(cancelLabel, DialogInterface.OnClickListener { dialog, which ->
                cancelSelected()
            })
        }
        return builder.create()
    }

}