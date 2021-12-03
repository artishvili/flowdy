package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.SearchSong
import kotlinx.coroutines.flow.Flow

interface ILyricsRepository {

    suspend fun getLyrics(song: SearchSong): String

    suspend fun storeSong(song: SearchSong, lyrics: String)

    fun observeStoredSongs(): Flow<List<SearchSong>>

    fun isSongStored(song: SearchSong): Flow<Boolean>

    suspend fun deleteSong(song: SearchSong, lyrics: String)
}
