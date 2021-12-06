package com.devshish.internship.presentation.ui.lyrics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.R
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.repository.ILyricsRepository
import com.devshish.internship.presentation.ui.utils.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber

// TODO process exceptions normally
class LyricsViewModel(
    private val searchSong: SearchSong,
    private val repository: ILyricsRepository
) : ViewModel() {

    val isLyricsStored: LiveData<Event<Int>>
        get() = _isLyricsStored
    private val _isLyricsStored = MutableLiveData<Event<Int>>()

    val noInternetConnection: LiveData<Event<Unit>>
        get() = _noInternetConnection
    private val _noInternetConnection = MutableLiveData<Event<Unit>>()

    private val lyricsFlow = MutableStateFlow<String?>(null)
    private val isStoredFlow = MutableStateFlow<Boolean?>(null)

    val uiState: LiveData<UIState>
        get() = _uiState
    private val _uiState = MutableLiveData<UIState>(UIState.IsLoading(searchSong))

    init {
        getLyrics()

        viewModelScope.launch {
            repository.isSongStored(searchSong).collect { isStored ->
                isStoredFlow.value = isStored
            }
        }

        viewModelScope.launch {
            lyricsFlow.combine(isStoredFlow) { lyrics, isStored ->
                Timber.d("UI STATE: Lyrics: ${lyrics?.take(5)} IsStored: $isStored")
                if (lyrics == null || isStored == null) {
                    UIState.IsLoading(searchSong)
                } else {
                    UIState.Loaded(searchSong, lyrics, isStored)
                }
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun getLyrics() {
        viewModelScope.launch {
            try {
                lyricsFlow.value = repository.getLyrics(searchSong)
            } catch (e: Exception) {
                _noInternetConnection.value = Event(Unit)
                Timber.e(e)
            }
        }
    }

    sealed class UIState {
        abstract val song: SearchSong

        data class IsLoading(override val song: SearchSong) : UIState()

        data class Loaded(
            override val song: SearchSong,
            val lyrics: String,
            val isStored: Boolean
        ) : UIState()
    }

    fun onStoreSongClick() {
        viewModelScope.launch {
            try {
                lyricsFlow.value?.let { repository.storeSong(searchSong, it) }
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                _isLyricsStored.value = Event(R.string.lyrics_saved)
            }
        }
    }

    fun onDeleteSongClick() {
        viewModelScope.launch {
            try {
                lyricsFlow.value?.let { repository.deleteSong(searchSong, it) }
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                _isLyricsStored.value = Event(R.string.lyrics_deleted)
            }
        }
    }
}
