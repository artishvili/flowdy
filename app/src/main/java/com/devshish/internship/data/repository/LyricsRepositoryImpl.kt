package com.devshish.internship.data.repository

import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.repository.ILyricsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class LyricsRepositoryImpl : ILyricsRepository {

    // TODO BUG! PAGE IS NOT ALWAYS RENDERED ENOUGH
    override suspend fun getLyrics(song: SearchSong): String =
        withContext(Dispatchers.IO) {
            val doc = Jsoup.connect(song.lyricsUri).get()
            doc.getElementsByClass("lyrics").text()
        }
}
