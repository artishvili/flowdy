package com.devshish.internship.di

import android.content.Context
import com.devshish.internship.data.repository.*
import com.devshish.internship.di.Albums.ALBUMS_LOCAL
import com.devshish.internship.di.Songs.*
import com.devshish.internship.domain.repository.*
import com.devshish.internship.domain.usecase.IAuthUseCase
import com.devshish.internship.presentation.service.player.client.MediaBrowserClient
import com.devshish.internship.presentation.ui.MainViewModel
import com.devshish.internship.presentation.ui.albums.details.AlbumDetailsViewModel
import com.devshish.internship.presentation.ui.albums.local.LocalAlbumsViewModel
import com.devshish.internship.presentation.ui.auth.AuthViewModel
import com.devshish.internship.presentation.ui.home.HomeViewModel
import com.devshish.internship.presentation.ui.lyrics.LyricsViewModel
import com.devshish.internship.presentation.ui.player.PlayerViewModel
import com.devshish.internship.presentation.ui.profile.ProfileViewModel
import com.devshish.internship.presentation.ui.search.SearchViewModel
import com.devshish.internship.presentation.ui.songs.savedlyrics.SavedLyricsViewModel
import com.devshish.internship.presentation.ui.songs.local.LocalSongsViewModel
import com.devshish.internship.presentation.ui.splash.SplashViewModel
import com.devshish.internship.presentation.ui.web.WebViewModel
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

enum class Albums {
    ALBUMS_LOCAL
}

enum class Songs {
    SONGS_LIKED,
    SONGS_LOCAL,
    SONGS_ALBUM
}

val appModule = module {
    // Client
    single { MediaBrowserClient(context = get()) }

    // Exo Player
    single<ExoPlayer> {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .build()
        SimpleExoPlayer.Builder(get())
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .build()
    }

    // Profile
    single<IProfileRepository> {
        ProfileRepositoryImpl(
            api = get()
        )
    }

    // Albums
    single<IAlbumsRepository>(named(ALBUMS_LOCAL)) {
        LocalAlbumsRepository(applicationContext = get())
    }

    // Songs
    single<ILyricsRepository>(named(SONGS_LIKED)) {
        LyricsRepositoryImpl(lyricsDAO = get())
    }
    single<ISongsRepository>(named(SONGS_LOCAL)) {
        LocalSongsRepository(applicationContext = get())
    }
    single<ISongsRepository>(named(SONGS_ALBUM)) { LikedSongsRepository() }

    // Lyrics
    single<ILyricsRepository> {
        LyricsRepositoryImpl(
            lyricsDAO = get()
        )
    }

    // Artists
    single<IHomeRepository> {
        HomeRepositoryImpl(
            api = get()
        )
    }

    single {
        androidApplication().applicationContext
            .getSharedPreferences(
                "com.devshish.internship.PREFERENCE_FILE_KEY",
                Context.MODE_PRIVATE
            )
    }

    single<ITokenRepository> {
        TokenRepositoryImpl(
            sharedPref = get()
        )
    }

    single<ISearchSongsRepository> {
        SearchAPIRepository(
            api = get()
        )
    }

    single<IAuthUseCase> {
        AuthUseCase(
            api = get(),
            tokenRepository = get()
        )
    }
}

val viewModelModule = module {
    // Main
    viewModel { MainViewModel(mediaBrowser = get()) }

    // Profile
    viewModel {
        ProfileViewModel(
            repository = get(),
            tokenRepository = get()
        )
    }

    // Albums
    viewModel { LocalAlbumsViewModel(repository = get(named(ALBUMS_LOCAL))) }
    viewModel { AlbumDetailsViewModel(repository = get(named(SONGS_ALBUM))) }

    // Songs
    viewModel { SavedLyricsViewModel(repository = get(named(SONGS_LIKED))) }
    viewModel {
        LocalSongsViewModel(
            repository = get(named(SONGS_LOCAL)),
            mediaBrowser = get()
        )
    }
    viewModel {
        PlayerViewModel(
            mediaBrowser = get(),
            repository = get()
        )
    }

    // Search
    viewModel {
        SearchViewModel(
            repository = get()
        )
    }

    // Auth
    viewModel {
        AuthViewModel(
            useCase = get()
        )
    }

    // WEB
    viewModel {
        WebViewModel(
            useCase = get()
        )
    }

    // Splash
    viewModel {
        SplashViewModel(
            repository = get()
        )
    }

    // Lyrics
    viewModel {
        LyricsViewModel(
            searchSong = get(),
            repository = get()
        )
    }

    // Home
    viewModel {
        HomeViewModel(
            repository = get()
        )
    }
}
