package com.devshish.internship.data.model.room

import androidx.core.net.toUri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devshish.internship.domain.model.SearchSong
import java.io.Serializable

// TODO SET UNIQUE FIELDS
@Entity(tableName = "lyrics")
data class RoomSong(
    val title: String,
    val artist: String,
    val imageUri: String?,
    val lyricsUri: String,
    val lyrics: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

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
