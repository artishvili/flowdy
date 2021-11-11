package com.devshish.internship.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.devshish.internship.domain.model.Lyrics

@Dao
interface LyricsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lyrics: Lyrics)
}
