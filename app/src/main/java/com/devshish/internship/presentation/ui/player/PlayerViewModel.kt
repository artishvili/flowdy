package com.devshish.internship.presentation.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.ui.service.client.MediaBrowserClient

class PlayerViewModel(
    private val mediaBrowser: MediaBrowserClient
) : ViewModel() {

    val songToPlay: LiveData<Song>
        get() = _songToPlay
    private val _songToPlay = MutableLiveData<Song>()

    val isPlaying: LiveData<Boolean>
        get() = _isPlaying
    private val _isPlaying = MutableLiveData<Boolean>()

    init {
        mediaBrowser.songCallback = {
            _songToPlay.value = it
        }

        mediaBrowser.isPlaying = {
            _isPlaying.value = it
        }
    }

    fun toggle() {
        mediaBrowser.toggle()
    }

    override fun onCleared() {
        super.onCleared()
        mediaBrowser.songCallback = null
    }
}
