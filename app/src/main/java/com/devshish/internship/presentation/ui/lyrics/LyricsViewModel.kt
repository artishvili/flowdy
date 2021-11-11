package com.devshish.internship.presentation.ui.lyrics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Lyrics
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.repository.ILyricsRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class LyricsViewModel(
    private val repository: ILyricsRepository
) : ViewModel() {

    val getLyricsEvent: LiveData<Lyrics>
        get() = _getLyricsEvent
    private val _getLyricsEvent = MutableLiveData<Lyrics>()

    val isProgressLoading: LiveData<Boolean>
        get() = _isProgressLoading
    private val _isProgressLoading = MutableLiveData<Boolean>()

    val isLyricsSaved: LiveData<Boolean>
        get() = _isLyricsSaved
    private val _isLyricsSaved = MutableLiveData<Boolean>()

    fun getLyrics(song: SearchSong) {
        viewModelScope.launch {
            _isProgressLoading.value = true
            try {
                _getLyricsEvent.value = repository.getLyrics(song)
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                _isProgressLoading.value = false
            }
        }
    }

    fun onLikeButtonClick() {
        viewModelScope.launch {
            _getLyricsEvent.value?.let { repository.saveLyrics(it) }
        }
        _isLyricsSaved.value = true
    }
}
