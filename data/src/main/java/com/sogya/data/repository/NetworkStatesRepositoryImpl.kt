package com.sogya.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sogya.domain.repository.NetworkStatesRepository


class NetworkStatesRepositoryImpl(context: Context) : NetworkStatesRepository,
    ConnectivityManager.NetworkCallback() {
    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI).addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).build()
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkStatesLiveData = MutableLiveData<Boolean>()

    init {
        connectivityManager.registerNetworkCallback(networkRequest, this)
        networkStatesLiveData.postValue(isNetworkAvailable(context))
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        networkStatesLiveData.postValue(true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        networkStatesLiveData.postValue(false)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    override fun getNetworkStatus(): LiveData<Boolean> = networkStatesLiveData
}