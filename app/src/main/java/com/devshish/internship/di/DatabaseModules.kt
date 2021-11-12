package com.devshish.internship.di

import android.app.Application
import androidx.room.Room
import com.devshish.internship.data.db.SongDatabase
import com.devshish.internship.data.db.RoomSongDao
import org.koin.dsl.module

fun provideDatabase(application: Application): SongDatabase =
    Room.databaseBuilder(application, SongDatabase::class.java, "lyrics_db")
        .fallbackToDestructiveMigration()
        .build()

fun provideDao(db: SongDatabase): RoomSongDao = db.getLyricsDao()

val dbModule = module {
    single { provideDatabase(application = get()) }
    single { provideDao(db = get()) }
}