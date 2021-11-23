package com.devshish.internship.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devshish.internship.data.model.room.RoomSong

@Dao
interface RoomSongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeSong(song: RoomSong)

    @Query("SELECT * FROM lyrics")
    suspend fun getLyrics(): List<RoomSong>

    @Query("SELECT * FROM lyrics WHERE title = :title AND artist = :artist")
    suspend fun getStoredSong(title: String, artist: String): RoomSong?
}