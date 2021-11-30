package com.devshish.internship.data.repository

import androidx.core.text.HtmlCompat
import androidx.core.text.toSpanned
import com.devshish.internship.data.db.RoomSongDao
import com.devshish.internship.data.model.room.RoomSong
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.repository.ILyricsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class LyricsRepositoryImpl(
    private val lyricsDAO: RoomSongDao
) : ILyricsRepository {

    // TODO BUG! PAGE IS NOT ALWAYS RENDERED ENOUGH
    override suspend fun getLyrics(song: SearchSong): String =
        withContext(Dispatchers.IO) {
            val roomSong = lyricsDAO.getStoredSong(song.title, song.artist)
            if (roomSong != null) {
                roomSong.lyrics
            } else {
                val doc = Jsoup.connect(song.lyricsUri).get()
                val html = doc.getElementsByClass("lyrics").html()
                HtmlCompat.fromHtml(
                    html,
                    HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH
                ).toString()
            }
        }

    override suspend fun storeSong(song: SearchSong, lyrics: String) {
        val roomSong = RoomSong.toRoomSearchSong(song, lyrics)
        lyricsDAO.storeSong(roomSong)
    }

    override suspend fun getStoredSongs(): List<SearchSong> =
        lyricsDAO.getLyrics().map { it.toSearchSong() }
}
