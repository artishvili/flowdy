package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.Song

interface ISongsRepository {

    suspend fun getSongs(): List<Song>
}
