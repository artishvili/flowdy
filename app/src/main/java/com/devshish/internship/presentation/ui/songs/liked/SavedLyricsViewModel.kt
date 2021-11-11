package com.devshish.internship.presentation.ui.songs.liked

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Lyrics
import com.devshish.internship.domain.repository.ILyricsRepository
import kotlinx.coroutines.launch

class SavedLyricsViewModel(
    private val repository: ILyricsRepository
) : ViewModel() {

    val likedSongs: LiveData<List<Lyrics>>
        get() = _likedSongs
    private val _likedSongs = MutableLiveData<List<Lyrics>>()

    init {
        viewModelScope.launch {
            _likedSongs.value = repository.getSavedLyrics()
        }
    }
}
