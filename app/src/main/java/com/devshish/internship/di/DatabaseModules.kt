package com.devshish.internship.di

import android.app.Application
import androidx.room.Room
import com.devshish.internship.data.db.LyricsDatabase
import com.devshish.internship.domain.dao.LyricsDAO
import org.koin.dsl.module

fun provideDatabase(application: Application): LyricsDatabase =
    Room.databaseBuilder(application, LyricsDatabase::class.java, "lyrics_db")
        .fallbackToDestructiveMigration()
        .build()

fun provideDao(db: LyricsDatabase): LyricsDAO = db.getLyricsDao()

val dbModule = module {
    single { provideDatabase(application = get()) }
    single { provideDao(db = get()) }
}