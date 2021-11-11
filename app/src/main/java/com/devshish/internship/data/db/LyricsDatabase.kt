package com.devshish.internship.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devshish.internship.domain.dao.LyricsDAO
import com.devshish.internship.domain.model.Lyrics

@Database(
    entities =[Lyrics::class],
    version = 1
)
abstract class LyricsDatabase : RoomDatabase() {

    abstract fun getLyricsDao(): LyricsDAO
}
