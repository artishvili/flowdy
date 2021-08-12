package com.devshish.internship

import android.app.Application
import timber.log.Timber

class MusicServiceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}