package com.devshish.internship.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.data.repository.SearchAPIRepository
import com.devshish.internship.domain.model.searchDTO.HitDTO
import com.devshish.internship.domain.repository.ISearchApiRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchAPIRepository
) : ViewModel() {

    val searching: LiveData<List<HitDTO>>
        get() = _searching
    private val _searching = MutableLiveData<List<HitDTO>>()

    fun searchSongs(query: String) {
        viewModelScope.launch {
            val response = repository.searchSongs(query)
            if (response.isSuccessful) {
                _searching.value = response.body()?.response?.hits
            }
        }
    }
}