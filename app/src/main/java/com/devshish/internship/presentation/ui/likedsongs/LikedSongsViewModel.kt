package com.devshish.internship.presentation.ui.likedsongs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.models.Song
import com.devshish.internship.presentation.SongsRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class LikedSongsViewModel(
    private val repository: SongsRepository
) : ViewModel() {

    val songs: LiveData<List<Song>>
        get() = _songs
    private val _songs = MutableLiveData<List<Song>>()

    fun getLikedSongs() = viewModelScope.launch {
        _songs.value = repository.getLikedSongs()
    }

    fun songClicked(song: Song) {
        Timber.d("Song clicked: $song")
    }
}
