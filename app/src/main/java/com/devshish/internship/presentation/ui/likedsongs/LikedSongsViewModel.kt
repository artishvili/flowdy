package com.devshish.internship.presentation.ui.likedsongs

import androidx.lifecycle.ViewModel
import com.devshish.internship.domain.models.Song
import com.devshish.internship.presentation.SongsRepository
import timber.log.Timber

class LikedSongsViewModel(
    private val repository: SongsRepository
) : ViewModel() {

    fun getLikedSongs() = repository.getLikedSongs()

    fun songClicked(song: Song) {
        Timber.d("Song clicked: $song")
    }
}
