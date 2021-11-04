package com.devshish.internship.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devshish.internship.presentation.service.player.client.MediaBrowserClient
import com.devshish.internship.presentation.ui.utils.Event

class MainViewModel(
    private val mediaBrowser: MediaBrowserClient
) : ViewModel() {

    val navigationEvent: LiveData<Event<Unit>>
        get() = _navigationEvent
    private val _navigationEvent = MutableLiveData<Event<Unit>>()

    fun onPlayerClick() {
        _navigationEvent.value = Event(Unit)
    }

    fun connect(): Unit = mediaBrowser.connect()

    fun disconnect(): Unit = mediaBrowser.disconnect()

    fun unregister(): Unit = mediaBrowser.unregister()
}
