package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.Song

interface ISongsRepository {

    var songToPlay: Song?

    suspend fun getSongs(): List<Song>
}
