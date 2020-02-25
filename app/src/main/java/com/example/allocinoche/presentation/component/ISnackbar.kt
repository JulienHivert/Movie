package com.example.allocinoche.presentation.component

import android.view.View

interface ISnackbar {

    fun displayError(errorMessage: String, view: View?)
    fun displayMessage(message: String, view: View?)
    fun displayMessageWithColor(message: String, color: Int, view: View?)

}