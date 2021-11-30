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
    private val searchSong: SearchSong,
    private val repository: ILyricsRepository
) : ViewModel() {

    val lyrics: LiveData<String>
        get() = _lyrics
    private val _lyrics = MutableLiveData<String>()

    val isProgressLoading: LiveData<Boolean>
        get() = _isProgressLoading
    private val _isProgressLoading = MutableLiveData<Boolean>()

    val isLyricsSaved: LiveData<Event<Unit>>
        get() = _isLyricsSaved
    private val _isLyricsSaved = MutableLiveData<Event<Unit>>()

    val song = MutableLiveData(searchSong)

    init {
        getLyrics(searchSong)
    }

    private fun getLyrics(song: SearchSong) {
        viewModelScope.launch {
            _isProgressLoading.value = true
            try {
                _lyrics.value = repository.getLyrics(song)
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                _isProgressLoading.value = false
            }
        }
    }

    fun onLikeButtonClick() {
        viewModelScope.launch {
            try {
                _lyrics.value?.let { repository.storeSong(searchSong, it) }
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                _isLyricsSaved.value = Event(Unit)
            }
        }
    }
}
