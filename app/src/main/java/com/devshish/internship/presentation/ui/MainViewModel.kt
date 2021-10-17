package com.devshish.internship.presentation.ui

import androidx.lifecycle.ViewModel
import com.devshish.internship.presentation.ui.service.client.MediaBrowserClient

class MainViewModel(
    private val mediaBrowser: MediaBrowserClient
) : ViewModel() {

    fun connect() = mediaBrowser.connect()

    fun disconnect() = mediaBrowser.disconnect()

    fun unregister() = mediaBrowser.unregister()
}
