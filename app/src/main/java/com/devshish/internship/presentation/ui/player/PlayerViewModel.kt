package com.devshish.internship.presentation.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.service.player.client.MediaBrowserClient
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

    val currentPosition: LiveData<Int>
        get() = _currentPosition
    private val _currentPosition = MutableLiveData<Int>()

    val playbackPosition: LiveData<String>
        get() = _playbackPosition
    private val _playbackPosition = MutableLiveData<String>()

    init {
        mediaBrowser.currentSongCallback = object : MediaBrowserClient.CurrentSongCallback {
            override fun updateSong(song: Song) {
                _songToPlay.value = song
            }

            override fun getState(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }

            override fun getPosition(position: Long) {
                _currentPosition.value = position.toInt()
            }
        }
    }

    fun check(): Boolean {
        return mediaBrowser.currentSongCallback != null
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
        mediaBrowser.currentSongCallback = null
    }
}
