package com.devshish.internship.presentation

import androidx.lifecycle.LiveData
import com.devshish.internship.domain.models.Song

interface LikedSongsRepository {

    fun getLikedSongs(): LiveData<List<Song>>
}