package com.devshish.internship.domain.repository

interface ILyricsRepository {

    suspend fun getLyrics(): String
}
