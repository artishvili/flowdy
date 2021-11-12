package com.devshish.internship.presentation.ui.lyrics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.repository.ILyricsRepository
import com.devshish.internship.presentation.ui.utils.Event
import kotlinx.coroutines.launch
import timber.log.Timber

class LyricsViewModel(
    private val repository: ILyricsRepository
) : ViewModel() {

    val getLyricsEvent: LiveData<String>
        get() = _getLyricsEvent
    private val _getLyricsEvent = MutableLiveData<String>()

    val isProgressLoading: LiveData<Boolean>
        get() = _isProgressLoading
    private val _isProgressLoading = MutableLiveData<Boolean>()

    val isLyricsSaved: LiveData<Event<Unit>>
        get() = _isLyricsSaved
    private val _isLyricsSaved = MutableLiveData<Event<Unit>>()

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

    fun onLikeButtonClick(searchSong: SearchSong) {
        viewModelScope.launch {
            try {
                _getLyricsEvent.value?.let { repository.storeSong(searchSong, it) }
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                _isLyricsSaved.value = Event(Unit)
            }
        }
    }
}
