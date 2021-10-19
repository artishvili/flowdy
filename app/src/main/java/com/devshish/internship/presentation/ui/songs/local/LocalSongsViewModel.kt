package com.devshish.internship.presentation.ui.songs.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Song
import com.devshish.internship.domain.repository.ISongsRepository
import com.devshish.internship.presentation.ui.service.client.MediaBrowserClient
import kotlinx.coroutines.launch

class LocalSongsViewModel(
    private val repository: ISongsRepository,
    private val mediaBrowser: MediaBrowserClient
) : ViewModel() {

    val localSongs: LiveData<List<Song>>
        get() = _localSongs
    private val _localSongs = MutableLiveData<List<Song>>()

    val navigationEvent: LiveData<Unit>
        get() = _navigationEvent
    private val _navigationEvent = MutableLiveData<Unit>()

    init {
        viewModelScope.launch {
            _localSongs.value = repository.getSongs()
        }
    }

    fun songClicked(song: Song) {
        mediaBrowser.setSong(song)
        _navigationEvent.value = Unit
    }
}
