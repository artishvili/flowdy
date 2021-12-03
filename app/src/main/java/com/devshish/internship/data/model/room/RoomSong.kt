package com.devshish.internship.data.model.room

import androidx.core.net.toUri
import androidx.room.Entity
import androidx.room.Index
import com.devshish.internship.domain.model.SearchSong
import java.io.Serializable

@Entity(
    tableName = "lyrics",
    indices = [Index(value = ["title", "artist"], unique = true)],
    primaryKeys = ["title", "artist"]
)
data class RoomSong(
    val title: String,
    val artist: String,
    val imageUri: String?,
    val lyricsUri: String,
    val lyrics: String
) : Serializable {

    companion object {
        fun toRoomSearchSong(searchSong: SearchSong, lyrics: String): RoomSong =
            RoomSong(
                title = searchSong.title,
                artist = searchSong.artist,
                imageUri = searchSong.imageUri.toString(),
                lyricsUri = searchSong.lyricsUri,
                lyrics = lyrics
            )
    }

    fun toSearchSong(): SearchSong =
        SearchSong(
            title = title,
            artist = artist,
            imageUri = imageUri?.toUri(),
            lyricsUri = lyricsUri
        )
}
