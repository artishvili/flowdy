package com.devshish.internship.presentation.ui.main.songs.liked

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Song
import com.devshish.internship.domain.repository.ISongsRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class LikedSongsViewModel(
    private val repository: ISongsRepository
) : ViewModel() {

    val likedSongs: LiveData<List<Song>>
        get() = _likedSongs
    private val _likedSongs = MutableLiveData<List<Song>>()

    init {
        viewModelScope.launch {
            _likedSongs.value = repository.getSongs()
        }
    }

    fun onSongClick(song: Song) {
        Timber.d("Song clicked: $song")
    }
}
