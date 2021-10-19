package com.devshish.internship.presentation.ui

import androidx.lifecycle.ViewModel
import com.devshish.internship.presentation.ui.service.client.MediaBrowserClient

class MainViewModel(
    private val mediaBrowser: MediaBrowserClient
) : ViewModel() {

    fun connect(): Unit = mediaBrowser.connect()

    fun disconnect(): Unit = mediaBrowser.disconnect()

    fun unregister(): Unit = mediaBrowser.unregister()
}
