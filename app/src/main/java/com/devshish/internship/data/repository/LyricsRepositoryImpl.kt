package com.devshish.internship.data.repository

import androidx.lifecycle.LiveData
import com.devshish.internship.domain.dao.LyricsDAO
import com.devshish.internship.domain.model.Lyrics
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.repository.ILyricsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class LyricsRepositoryImpl(
    private val lyricsDAO: LyricsDAO
) : ILyricsRepository {

    // TODO BUG! PAGE IS NOT ALWAYS RENDERED ENOUGH
    override suspend fun getLyrics(song: SearchSong): Lyrics =
        withContext(Dispatchers.IO) {
            val doc = Jsoup.connect(song.lyricsUri).get()
            val content = doc.getElementsByClass("lyrics").text()
            Lyrics(
                title = song.title,
                artist = song.artist,
                content = content,
                imageUri = song.imageUri?.toString()
            )
        }

    override suspend fun saveLyrics(lyrics: Lyrics) {
        lyricsDAO.insertLyrics(lyrics)
    }

    override suspend fun getSavedLyrics(): List<Lyrics> = lyricsDAO.getLyrics()
}
