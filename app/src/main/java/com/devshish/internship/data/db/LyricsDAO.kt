package com.devshish.internship.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devshish.internship.data.model.room.RoomSong

@Dao
interface LyricsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLyrics(lyrics: RoomSong)

    @Query("SELECT * FROM lyrics")
    suspend fun getLyrics(): List<RoomSong>
}
