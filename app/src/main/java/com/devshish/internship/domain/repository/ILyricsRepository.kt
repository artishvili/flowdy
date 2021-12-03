package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.SearchSong
import kotlinx.coroutines.flow.Flow

interface ILyricsRepository {

    suspend fun getLyrics(song: SearchSong): String

    suspend fun storeSong(song: SearchSong, lyrics: String)

    suspend fun getStoredSongs(): List<SearchSong>

    fun isSongStored(song: SearchSong): Flow<Boolean>
}
