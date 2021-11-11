package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.Lyrics
import com.devshish.internship.domain.model.SearchSong

interface ILyricsRepository {

    suspend fun getLyrics(song: SearchSong): Lyrics

    suspend fun saveLyrics(lyrics: Lyrics)

    suspend fun getSavedLyrics(): List<Lyrics>
}
