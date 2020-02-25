package com.example.allocinoche.presentation.component

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.allocinoche.R
import com.google.android.material.snackbar.Snackbar

class SnackbarComponent(private val context: Context): ISnackbar {

    override fun displayError(errorMessage: String, view: View?) {
        displayMessageWithColor(errorMessage, ContextCompat.getColor(context, android.R.color.holo_red_light), view)
    }

    override fun displayMessage(message: String, view: View?) {
        displayMessageWithColor(message, ContextCompat.getColor(context, android.R.color.holo_green_light), view)
    }

    override fun displayMessageWithColor(message: String, color: Int, view: View?) {
        view?.let {
            val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_LONG)
            val snackbarView = snackbar.view
            val textView = snackbarView.findViewById(R.id.snackbar_text) as TextView
            snackbarView.setBackgroundColor(color)
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            snackbar.show()
        }
    }
}