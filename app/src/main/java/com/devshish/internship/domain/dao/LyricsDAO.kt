package com.devshish.internship.domain.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devshish.internship.domain.model.Lyrics

@Dao
interface LyricsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLyrics(lyrics: Lyrics)

    @Query("SELECT * FROM lyrics")
    suspend fun getLyrics(): List<Lyrics>
}
