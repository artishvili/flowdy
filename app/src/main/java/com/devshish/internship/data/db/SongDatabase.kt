package com.devshish.internship.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devshish.internship.data.model.room.RoomSong

@Database(
    entities = [RoomSong::class],
    version = 4
)
abstract class SongDatabase : RoomDatabase() {

    abstract fun getLyricsDao(): RoomSongDao
}
