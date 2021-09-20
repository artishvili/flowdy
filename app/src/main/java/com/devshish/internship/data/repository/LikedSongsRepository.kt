package com.devshish.internship.data.repository

import com.devshish.internship.domain.model.Song
import com.devshish.internship.domain.repository.ISongsRepository

class LikedSongsRepository : ISongsRepository {

    private val songs = emptyList<Song>()

    override suspend fun getSongs(): List<Song> = songs
}
