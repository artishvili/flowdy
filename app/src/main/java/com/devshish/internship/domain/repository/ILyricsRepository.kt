package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.SearchSong

interface ILyricsRepository {

    suspend fun getLyrics(song: SearchSong): String
}