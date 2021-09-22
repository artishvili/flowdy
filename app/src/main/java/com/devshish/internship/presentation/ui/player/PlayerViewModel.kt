package com.devshish.internship.presentation.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Song
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val serviceConnection: MusicServiceConnection
) : ViewModel() {

    val isPlaying: LiveData<Boolean>
        get() = _isPlaying
    private val _isPlaying = MutableLiveData<Boolean>()

    val currentSong: LiveData<Song>
        get() = _currentSong
    private val _currentSong = MutableLiveData<Song>()

    val currentPosition: LiveData<Long>
        get() = _currentPosition
    private val _currentPosition = MutableLiveData<Long>()

    init {
        serviceConnection.isPlaying = {
            _isPlaying.value = it
        }

        serviceConnection.currentSong = {
            _currentSong.value = it
        }

        serviceConnection.currentPosition = {
            _currentPosition.value = it
        }

        // updateCurrentPlayerPosition()
    }

    fun toggle(song: Song) {
        if (song.mediaId == _currentSong.value?.mediaId) {
            _isPlaying.value?.let {
                when {
                    it -> serviceConnection.transportControls.pause()
                    !it -> serviceConnection.transportControls.play()
                }
            }
        } else {
            serviceConnection.transportControls.playFromMediaId(song.mediaId, null)
        }
    }

    fun seekTo(position: Int) {
        serviceConnection.transportControls.seekTo(position.toLong())
    }

    /*private fun updateCurrentPlayerPosition() {
        viewModelScope.launch {
            while (true) {
                val position = _currentPlaybackPosition.value
                if (currentPosition.value != position) {
                    _currentPosition.postValue(position!!)
                }
            }
        }
    }*/
}
