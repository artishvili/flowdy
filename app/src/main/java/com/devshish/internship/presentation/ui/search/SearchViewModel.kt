package com.devshish.internship.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.repository.ISearchSongsRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel(
    private val repository: ISearchSongsRepository
) : ViewModel() {

    val isDescriptionVisible: LiveData<Boolean>
        get() = _isDescriptionVisible
    private val _isDescriptionVisible = MutableLiveData<Boolean>()

    val isProgressLoading: LiveData<Boolean>
        get() = _isProgressLoading
    private val _isProgressLoading = MutableLiveData<Boolean>()

    val songsList: LiveData<List<SearchSong>>
        get() = _songsList
    private val _songsList = MutableLiveData<List<SearchSong>>()

    val navigateToLyricsEvent: LiveData<Unit>
        get() = _navigateToLyricsEvent
    private val _navigateToLyricsEvent = MutableLiveData<Unit>()

    fun onSearchSongClick() {
        _navigateToLyricsEvent.value = Unit
    }

    fun searchSongs(query: String) {
        viewModelScope.launch {
            _isProgressLoading.value = true
            _isDescriptionVisible.value = false
            try {
                _songsList.value = repository.searchSongs(query)
            } catch (e: Exception) {
                _isDescriptionVisible.value = true
                Timber.e(e)
            } finally {
                _isProgressLoading.value = false
            }
        }
    }
}
