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

    sealed class UIState {
        data class Exception(@StringRes val messageRes: Int) : UIState()
        data class Navigate(val searchSong: SearchSong) : UIState()
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

    val uiState: LiveData<Event<UIState>>
        get() = _uiState
    private val _uiState = MutableLiveData<Event<UIState>>()

    fun onSearchSongClick(song: SearchSong) {
        _uiState.value = Event(UIState.Navigate(song))
    }

    fun searchSongs(query: String) {
        viewModelScope.launch {
            _isProgressLoading.value = true
            _isDescriptionVisible.value = false
            try {
                _songsList.value = repository.searchSongs(query)
            } catch (e: Exception) {
                _uiState.value = Event(UIState.Exception(R.string.snackbar_something_went_wrong))
                _isDescriptionVisible.value = true
                Timber.e(e)
            } finally {
                _isProgressLoading.value = false
            }
        }
    }
}
