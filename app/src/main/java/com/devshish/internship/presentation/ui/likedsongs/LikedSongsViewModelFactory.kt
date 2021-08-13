package com.devshish.internship.presentation.ui.likedsongs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devshish.internship.presentation.SongsRepository

class LikedSongsViewModelFactory(
    private val repository: SongsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LikedSongsViewModel::class.java)) {
            return LikedSongsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}