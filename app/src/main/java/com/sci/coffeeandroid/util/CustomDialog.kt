package com.sci.coffeeandroid.util

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sci.coffeeandroid.R

 fun showSuccessDialog(
    context: Context,
    title: String,
    message: String,
    onClick : () -> Unit
) {
    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setIcon(R.drawable.ic_check_circle) // Replace with your success icon
        .setPositiveButton("OK") { dialog, which ->
            onClick()
        }
        .show()
}