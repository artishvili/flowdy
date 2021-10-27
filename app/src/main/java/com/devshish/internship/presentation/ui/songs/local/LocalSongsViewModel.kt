package com.devshish.internship.presentation.ui.songs.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Song
import com.devshish.internship.domain.repository.ISongsRepository
import com.devshish.internship.presentation.service.player.client.MediaBrowserClient
import com.devshish.internship.presentation.ui.utils.Event
import kotlinx.coroutines.launch

class LocalSongsViewModel(
    private val repository: ISongsRepository,
    private val mediaBrowser: MediaBrowserClient
) : ViewModel() {

    val localSongs: LiveData<List<Song>>
        get() = _localSongs
    private val _localSongs = MutableLiveData<List<Song>>()

    val navigationEvent: LiveData<Event<Unit>>
        get() = _navigationEvent
    private val _navigationEvent = MutableLiveData<Event<Unit>>()

    init {
        viewModelScope.launch {
            _localSongs.value = repository.getSongs()
        }
    }

    fun songClicked(song: Song) {
        mediaBrowser.playSong(song)
        _navigationEvent.value = Event(Unit)
    }
}
