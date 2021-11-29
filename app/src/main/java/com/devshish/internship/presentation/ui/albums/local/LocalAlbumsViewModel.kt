package com.devshish.internship.presentation.ui.albums.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Album
import com.devshish.internship.domain.repository.IAlbumsRepository
import kotlinx.coroutines.launch

@Deprecated("")
class LocalAlbumsViewModel(
    private val repository: IAlbumsRepository
) : ViewModel() {

    val localAlbums: LiveData<List<Album>>
        get() = _localAlbums
    private val _localAlbums = MutableLiveData<List<Album>>()

    init {
        viewModelScope.launch {
            _localAlbums.value = repository.getAlbums()
        }
    }
}
