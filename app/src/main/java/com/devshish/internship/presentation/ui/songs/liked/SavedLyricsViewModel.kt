package com.devshish.internship.presentation.ui.songs.liked

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Lyrics
import com.devshish.internship.domain.repository.ILyricsRepository
import com.devshish.internship.presentation.ui.utils.Event
import kotlinx.coroutines.launch

class SavedLyricsViewModel(
    private val repository: ILyricsRepository
) : ViewModel() {

    val savedSongs: LiveData<List<Lyrics>>
        get() = _savedSongs
    private val _savedSongs = MutableLiveData<List<Lyrics>>()

    val navigationEvent: LiveData<Event<Lyrics>>
        get() = _navigationEvent
    private val _navigationEvent = MutableLiveData<Event<Lyrics>>()

    init {
        viewModelScope.launch {
            _savedSongs.value = repository.getSavedLyrics()
        }
    }

    fun onLyricsClick(lyrics: Lyrics) {
        _navigationEvent.value = Event(lyrics)
    }
}
