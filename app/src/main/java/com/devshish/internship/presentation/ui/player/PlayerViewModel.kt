package com.devshish.internship.presentation.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Song
import com.devshish.internship.domain.repository.ISongsRepository
import com.devshish.internship.presentation.ui.service.client.MediaBrowserClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerViewModel(
    repository: ISongsRepository,
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

    init {
        _songToPlay.value = repository.songToPlay
        mediaBrowser.playFromUri(repository.songToPlay?.uri)
        getState()
        updatePosition()
    }

    fun getState() {
        _isPlaying.value = mediaBrowser.isPlaying
    }

    fun toggle() = mediaBrowser.toggle()

    fun seekTo(position: Long) = mediaBrowser.seekTo(position)

    private fun updatePosition() {
        viewModelScope.launch {
            while (true) {
                val position = mediaBrowser.getPosition()
                if (currentPosition.value != position) {
                    _currentPosition.postValue(position)
                }
                delay(100L)
            }
        }
    }
}
