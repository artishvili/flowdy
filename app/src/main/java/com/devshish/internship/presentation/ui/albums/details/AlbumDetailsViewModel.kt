package com.devshish.internship.presentation.ui.albums.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Song
import com.devshish.internship.domain.repository.ISongsRepository
import kotlinx.coroutines.launch

@Deprecated("FOR NOW")
class AlbumDetailsViewModel(
    private val repository: ISongsRepository
) : ViewModel() {

    val albumSongs: LiveData<List<Song>>
        get() = _albumSongs
    private val _albumSongs = MutableLiveData<List<Song>>()

    init {
        viewModelScope.launch {
            _albumSongs.value = repository.getSongs()
        }
    }
}
