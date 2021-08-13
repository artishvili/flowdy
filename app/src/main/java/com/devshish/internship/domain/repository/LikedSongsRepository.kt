package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.Song

interface LikedSongsRepository {

    suspend fun getLikedSongs(): List<Song>
}