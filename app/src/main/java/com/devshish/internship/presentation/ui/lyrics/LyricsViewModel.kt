package com.devshish.internship.presentation.ui.lyrics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.repository.ILyricsRepository
import kotlinx.coroutines.launch

class LyricsViewModel(
    private val repository: ILyricsRepository
) : ViewModel() {

    val getLyricsEvent: LiveData<String>
        get() = _getLyricsEvent
    private val _getLyricsEvent = MutableLiveData<String>()

    fun getLyrics(song: SearchSong) {
        viewModelScope.launch {
            val result = repository.getLyrics(song)
            _getLyricsEvent.value = result
        }
    }
}
