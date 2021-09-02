package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.Song

interface ILocalSongsRepository {

    suspend fun getLocalSongs(): List<Song>
}
