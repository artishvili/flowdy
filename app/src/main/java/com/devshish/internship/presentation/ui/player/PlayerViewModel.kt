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

    init {
        mediaBrowser.songCallback = {
            _songToPlay.value = it
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
