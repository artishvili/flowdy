package com.devshish.internship.presentation.ui.library.likedalbums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Album
import com.devshish.internship.domain.repository.IAlbumsRepository
import kotlinx.coroutines.launch

class AlbumsViewModel(
    private val repository: IAlbumsRepository
) : ViewModel() {

    val albums: LiveData<List<Album>>
        get() = _albums
    private val _albums = MutableLiveData<List<Album>>()

    init {
        viewModelScope.launch {
            _albums.value = repository.getLikedAlbums()
        }
    }
}
