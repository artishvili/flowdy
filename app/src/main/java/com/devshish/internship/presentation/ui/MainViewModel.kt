package com.devshish.internship.presentation.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devshish.internship.MusicServiceApplication
import com.devshish.internship.presentation.service.player.client.MediaBrowserClient
import com.devshish.internship.presentation.ui.utils.Event

class MainViewModel(
    app: Application,
    private val mediaBrowser: MediaBrowserClient
) : AndroidViewModel(app) {

    val navigationEvent: LiveData<Event<Unit>>
        get() = _navigationEvent
    private val _navigationEvent = MutableLiveData<Event<Unit>>()

    fun checkInternetConnection(): Boolean {
        val connManager = getApplication<MusicServiceApplication>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connManager.activeNetwork ?: return false
            val activeNetwork = connManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> true
                else -> false
            }
        } else {
            return connManager.activeNetworkInfo?.isConnected ?: false
        }
    }

    fun onPlayerClick() {
        _navigationEvent.value = Event(Unit)
    }

    fun connect(): Unit = mediaBrowser.connect()

    fun disconnect(): Unit = mediaBrowser.disconnect()

    fun unregister(): Unit = mediaBrowser.unregister()
}
