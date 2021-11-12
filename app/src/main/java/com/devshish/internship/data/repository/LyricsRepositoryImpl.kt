package com.devshish.internship.data.repository

import com.devshish.internship.data.db.LyricsDAO
import com.devshish.internship.data.model.room.RoomSong
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.repository.ILyricsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class LyricsRepositoryImpl(
    private val lyricsDAO: LyricsDAO
) : ILyricsRepository {

    // TODO BUG! PAGE IS NOT ALWAYS RENDERED ENOUGH
    override suspend fun getLyrics(song: SearchSong): String =
        song.lyrics ?: withContext(Dispatchers.IO) {
            val doc = Jsoup.connect(song.lyricsUri!!).get()
            doc.getElementsByClass("lyrics").text()
        }

    override suspend fun storeSong(song: SearchSong, lyrics: String) {
        val roomSong = RoomSong.toRoomSearchSong(song, lyrics)
        lyricsDAO.insertLyrics(roomSong)
    }

    override suspend fun getStoredSongs(): List<SearchSong> =
        lyricsDAO.getLyrics().map { it.toSearchSong() }
}
