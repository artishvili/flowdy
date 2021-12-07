package com.devshish.internship.presentation.ui.search

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.R
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.repository.ISearchSongsRepository
import com.devshish.internship.presentation.ui.utils.Event
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel(
    private val repository: ISearchSongsRepository
) : ViewModel() {

    sealed class UIEvent {
        data class NetworkError(@StringRes val messageRes: Int) : UIEvent()
        data class NavigateToSong(val searchSong: SearchSong) : UIEvent()
    }

    val isDescriptionVisible: LiveData<Boolean>
        get() = _isDescriptionVisible
    private val _isDescriptionVisible = MutableLiveData<Boolean>()

    val isProgressLoading: LiveData<Boolean>
        get() = _isProgressLoading
    private val _isProgressLoading = MutableLiveData<Boolean>()

    val songsList: LiveData<List<SearchSong>>
        get() = _songsList
    private val _songsList = MutableLiveData<List<SearchSong>>()

    val uiEvent: LiveData<Event<UIEvent>>
        get() = _uiEvent
    private val _uiEvent = MutableLiveData<Event<UIEvent>>()

    fun onSearchSongClick(song: SearchSong) {
        _uiEvent.value = Event(UIEvent.NavigateToSong(song))
    }

    fun searchSongs(query: String) {
        viewModelScope.launch {
            _isProgressLoading.value = true
            _isDescriptionVisible.value = false
            try {
                _songsList.value = repository.searchSongs(query)
            } catch (e: Exception) {
                _uiEvent.value = Event(UIEvent.NetworkError(R.string.snackbar_something_went_wrong))
                _isDescriptionVisible.value = true
                Timber.e(e)
            } finally {
                _isProgressLoading.value = false
            }
        }
    }
}
