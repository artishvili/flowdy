package com.devshish.internship.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Artist
import com.devshish.internship.domain.repository.IArtistRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val artistRepo: IArtistRepository
) : ViewModel() {

    val topArtists: LiveData<List<Artist>>
        get() = _topArtists
    private val _topArtists = MutableLiveData<List<Artist>>()

    init {
        viewModelScope.launch {
            _topArtists.value = artistRepo.getTopArtists()
        }
    }
}
