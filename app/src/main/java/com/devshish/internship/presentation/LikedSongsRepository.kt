package com.devshish.internship.presentation

import com.devshish.internship.domain.models.Song

interface LikedSongsRepository {

    suspend fun getLikedSongs(): List<Song>
}