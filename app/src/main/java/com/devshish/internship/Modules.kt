package com.devshish.internship

import com.devshish.internship.Albums.ALBUMS_LIKED
import com.devshish.internship.Albums.ALBUMS_LOCAL
import com.devshish.internship.Songs.*
import com.devshish.internship.data.repository.*
import com.devshish.internship.domain.repository.IAlbumsRepository
import com.devshish.internship.domain.repository.IProfileRepository
import com.devshish.internship.domain.repository.ISongsRepository
import com.devshish.internship.presentation.ui.albums.details.AlbumDetailsViewModel
import com.devshish.internship.presentation.ui.albums.liked.LikedAlbumsViewModel
import com.devshish.internship.presentation.ui.albums.local.LocalAlbumsViewModel
import com.devshish.internship.presentation.ui.player.MusicService
import com.devshish.internship.presentation.ui.player.MusicServiceConnection
import com.devshish.internship.presentation.ui.player.PlayerViewModel
import com.devshish.internship.presentation.ui.profile.EditProfileViewModel
import com.devshish.internship.presentation.ui.profile.ProfileViewModel
import com.devshish.internship.presentation.ui.songs.liked.LikedSongsViewModel
import com.devshish.internship.presentation.ui.songs.local.LocalSongsViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

enum class Albums {
    ALBUMS_LIKED,
    ALBUMS_LOCAL
}

enum class Songs {
    SONGS_LIKED,
    SONGS_LOCAL,
    SONGS_ALBUM
}

val appModule = module {

    // Profile
    single<IProfileRepository> { ProfileRepository() }

    // Albums
    single<IAlbumsRepository>(
        named(ALBUMS_LIKED)
    ) { LikedAlbumsRepository() }

    single<IAlbumsRepository>(
        named(ALBUMS_LOCAL)
    ) {
        LocalAlbumsRepository(
            applicationContext = get()
        )
    }

    // Songs
    single<ISongsRepository>(
        named(SONGS_LIKED)
    ) { LikedSongsRepository() }

    single<ISongsRepository>(
        named(SONGS_LOCAL)
    ) {
        LocalSongsRepository(
            applicationContext = get()
        )
    }

    single<ISongsRepository>(
        named(SONGS_ALBUM)
    ) { LikedSongsRepository() }
}

val viewModelModule = module {

    /*
    * Profile
    * */
    viewModel {
        ProfileViewModel(
            repository = get()
        )
    }

    viewModel {
        EditProfileViewModel(
            repository = get()
        )
    }

    /*
    * Albums
    * */
    viewModel {
        LikedAlbumsViewModel(
            repository = get(
                named(ALBUMS_LIKED)
            )
        )
    }

    viewModel {
        LocalAlbumsViewModel(
            repository = get(
                named(ALBUMS_LOCAL)
            )
        )
    }

    viewModel {
        AlbumDetailsViewModel(
            repository = get(
                named(SONGS_ALBUM)
            )
        )
    }

    /*
    * Songs
    * */
    viewModel {
        LikedSongsViewModel(
            repository = get(
                named(SONGS_LIKED)
            )
        )
    }

    viewModel {
        LocalSongsViewModel(
            repository = get(
                named(SONGS_LOCAL)
            )
        )
    }

    viewModel {
        PlayerViewModel(
            serviceConnection = get()
        )
    }
}

val serviceModule = module {

    // Data source factory
    single<DataSource.Factory> {
        DefaultDataSourceFactory(
            get(),
            Util.getUserAgent(get(), "Internship")
        )
    }

    // Exo player
    single<ExoPlayer> {
        SimpleExoPlayer.Builder(get()).build()
    }

    // Songs repository
    single<ISongsRepository> {
        LocalSongsRepository(
            applicationContext = get()
        )
    }

    single {
        MusicService()
    }

    single {
        MusicServiceConnection(
            context = get()
        )
    }
}
