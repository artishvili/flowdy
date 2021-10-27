package com.devshish.internship.presentation.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.ui.service.client.MediaBrowserClient
import com.devshish.internship.presentation.ui.utils.convertMillisToTime

class PlayerViewModel(
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

    val playbackPosition: LiveData<String>
        get() = _playbackPosition
    private val _playbackPosition = MutableLiveData<String>()

    init {
        mediaBrowser.songCallback = {
            _songToPlay.value = it
        }

        mediaBrowser.isPlaying = {
            _isPlaying.value = it
        }

        mediaBrowser.currentPosition = {
            _currentPosition.value = it
        }
    }

    fun toggle() {
        mediaBrowser.toggle()
    }

    fun seekTo(position: Long) {
        mediaBrowser.seekTo(position)
    }

    fun onProgressChanged(progress: Int) {
        _playbackPosition.value = convertMillisToTime(progress)
    }

    override fun onCleared() {
        super.onCleared()
        mediaBrowser.apply {
            songCallback = null
            isPlaying = null
            currentPosition = null
        }
    }
}
