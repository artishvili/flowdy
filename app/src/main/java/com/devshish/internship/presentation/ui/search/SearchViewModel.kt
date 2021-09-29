package com.devshish.internship.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.model.Song
import com.devshish.internship.domain.repository.ISearchSongsRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel(
    private val repository: ISearchSongsRepository
) : ViewModel() {

    val isVisible: LiveData<Boolean>
        get() = _isVisible
    private val _isVisible = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()

    val searching: LiveData<List<SearchSong>>
        get() = _searching
    private val _searching = MutableLiveData<List<SearchSong>>()

    fun searchSongs(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _isVisible.value = false
            try {
                _searching.value = repository.searchSongs(query)
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
