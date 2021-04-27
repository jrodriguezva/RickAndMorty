package com.jrodriguezva.rickandmortykotlin.framework.network

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import androidx.annotation.RequiresPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Suppress("DEPRECATION")
class NetworkUtils @Inject constructor(@ApplicationContext private val context: Context) {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val actNw = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
            when {
                actNw.hasTransport(TRANSPORT_WIFI) || actNw.hasTransport(TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }
}
