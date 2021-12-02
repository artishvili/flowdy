package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.SearchSong

interface ILyricsRepository {

    suspend fun getLyrics(song: SearchSong): String

    suspend fun storeSong(song: SearchSong, lyrics: String)

    suspend fun getStoredSongs(): List<SearchSong>

    suspend fun isSongStored(song: SearchSong): Boolean
}
