package com.devshish.internship.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.service.player.client.MediaBrowserClient
import com.devshish.internship.presentation.ui.utils.Event

class MainViewModel(
    private val mediaBrowser: MediaBrowserClient
) : ViewModel() {

    val songToPlay: LiveData<Song>
        get() = _songToPlay
    private val _songToPlay = MutableLiveData<Song>()

    val isPlaying: LiveData<Boolean>
        get() = _isPlaying
    private val _isPlaying = MutableLiveData<Boolean>()

    val currentPosition: LiveData<Long>
        get() = _currentPosition
    private val _currentPosition = MutableLiveData<Long>()

    val navigationEvent: LiveData<Event<Unit>>
        get() = _navigationEvent
    private val _navigationEvent = MutableLiveData<Event<Unit>>()

    val playerBarVisibility: LiveData<Boolean>
        get() = _playerBarVisibility
    private val _playerBarVisibility = MutableLiveData<Boolean>()

    init {
        mediaBrowser.currentSongCallback = object : MediaBrowserClient.CurrentSongCallback {
            override fun updateSong(song: Song) {
                _songToPlay.value = song
                _playerBarVisibility.value = true
            }

            override fun getState(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }

            override fun getPosition(position: Long) {
                _currentPosition.value = position
            }
        }
    }

    fun onPlayerClick() {
        _playerBarVisibility.value = false
        _navigationEvent.value = Event(Unit)
    }

    fun toggle(): Unit = mediaBrowser.toggle()

    fun connect(): Unit = mediaBrowser.connect()

    fun disconnect(): Unit = mediaBrowser.disconnect()

    fun unregister(): Unit = mediaBrowser.unregister()
}
