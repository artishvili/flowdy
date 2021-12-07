package com.devshish.internship.data.repository

import androidx.core.text.HtmlCompat
import com.devshish.internship.data.db.RoomSongDao
import com.devshish.internship.data.model.room.RoomSong
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.repository.ILyricsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import timber.log.Timber

class LyricsRepositoryImpl(
    private val lyricsDAO: RoomSongDao
) : ILyricsRepository {

    override suspend fun getLyrics(song: SearchSong): String =
        withContext(Dispatchers.IO) {
            val roomSong = lyricsDAO.getStoredSong(song.title, song.artist)
            if (roomSong != null) {
                roomSong.lyrics
            } else {
                var lyrics = ""
                while (lyrics.isEmpty()) {
                    val doc = Jsoup.connect(song.lyricsUri).get()
                    doc.getElementById("lyrics-root")?.html()?.let { html ->
                        lyrics = HtmlCompat.fromHtml(
                            html,
                            HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH
                        ).toString()
                    }
                }
                lyrics
            }
        }

    override suspend fun storeSong(song: SearchSong, lyrics: String) {
        val roomSong = RoomSong.toRoomSearchSong(song, lyrics)
        lyricsDAO.storeSong(roomSong)
    }

    override fun observeStoredSongs(): Flow<List<SearchSong>> =
        lyricsDAO.getStoredSongs().map { roomSongList ->
            roomSongList.map { roomSong ->
                roomSong.toSearchSong()
            }
        }

    override fun isSongStored(song: SearchSong): Flow<Boolean> {
        return lyricsDAO.isSongStored(song.title, song.artist)
    }

    override suspend fun deleteSong(song: SearchSong, lyrics: String) {
        val roomSong = RoomSong.toRoomSearchSong(song, lyrics)
        Timber.d("$roomSong")
        lyricsDAO.deleteSong(roomSong)
    }
}
