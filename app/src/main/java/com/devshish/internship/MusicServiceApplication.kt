package com.devshish.internship

import android.app.Application
import com.devshish.internship.di.appModule
import com.devshish.internship.di.dbModule
import com.devshish.internship.di.networkModule
import com.devshish.internship.di.viewModelModule
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
                    networkModule,
                    dbModule
                )
            )
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
