package com.devshish.internship

import android.app.Application
import com.devshish.internship.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MusicServiceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MusicServiceApplication)
            modules(
                listOf(
                    appModule,
                    viewModelModule,
                    geniusModule,
                    lastFmModule,
                    dbModule
                )
            )
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
