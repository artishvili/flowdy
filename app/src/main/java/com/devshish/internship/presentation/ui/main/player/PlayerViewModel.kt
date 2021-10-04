package com.devshish.internship.presentation.ui.main.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Song
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {

    private val song = Song(null, "Pure Souls", "Kanye West", 300, null)

    val songToPlay: LiveData<Song>
        get() = _songToPlay
    private val _songToPlay = MutableLiveData<Song>()

    val pauseEvent: LiveData<Unit>
        get() = _pauseEvent
    private val _pauseEvent = MutableLiveData<Unit>()

    val playEvent: LiveData<Unit>
        get() = _playEvent
    private val _playEvent = MutableLiveData<Unit>()

    init {
        viewModelScope.launch {
            _songToPlay.value = song
        }
    }

    fun onPause() {
        _pauseEvent.value = Unit
    }

    fun onPlay() {
        _playEvent.value = Unit
    }
}
