package com.devshish.internship.presentation.ui.songs.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Song
import com.devshish.internship.domain.repository.ISongsRepository
import kotlinx.coroutines.launch

class LocalSongsViewModel(
    private val repository: ISongsRepository
) : ViewModel() {

    val localSongs: LiveData<List<Song>>
        get() = _localSongs
    private val _localSongs = MutableLiveData<List<Song>>()

    init {
        viewModelScope.launch {
            _localSongs.value = repository.getSongs()
        }
    }

    fun sendSong(song: Song) {
        repository.songToPlay = song
    }
}
