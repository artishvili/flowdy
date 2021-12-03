package com.devshish.internship.presentation.ui.songs.savedlyrics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.repository.ILyricsRepository
import com.devshish.internship.presentation.ui.utils.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SavedLyricsViewModel(
    private val repository: ILyricsRepository
) : ViewModel() {

    val savedSongs: LiveData<List<SearchSong>>
        get() = _savedSongs
    private val _savedSongs = MutableLiveData<List<SearchSong>>()

    val navigationEvent: LiveData<Event<SearchSong>>
        get() = _navigationEvent
    private val _navigationEvent = MutableLiveData<Event<SearchSong>>()

    init {
        viewModelScope.launch {
            repository.getStoredSongs().collect { songList ->
                _savedSongs.value = songList
            }
        }
    }

    fun onLyricsClick(song: SearchSong) {
        _navigationEvent.value = Event(song)
    }
}
