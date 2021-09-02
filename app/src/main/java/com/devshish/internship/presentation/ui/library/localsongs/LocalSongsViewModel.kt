package com.devshish.internship.presentation.ui.library.localsongs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Song
import com.devshish.internship.domain.repository.ILocalSongsRepository
import kotlinx.coroutines.launch

class LocalSongsViewModel(
    private val repository: ILocalSongsRepository
) : ViewModel() {

    val localSongs: LiveData<List<Song>>
        get() = _localSongs
    private val _localSongs = MutableLiveData<List<Song>>()

    init {
        viewModelScope.launch {
            _localSongs.value = repository.getLocalSongs()
        }
    }
}