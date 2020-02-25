package com.example.allocinoche.presentation.component

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object InternetCheck {

    fun isConnected(context : Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }
}