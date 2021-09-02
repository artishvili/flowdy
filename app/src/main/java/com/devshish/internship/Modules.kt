package com.devshish.internship

import com.devshish.internship.data.repository.AlbumsRepository
import com.devshish.internship.data.repository.LikedSongsRepository
import com.devshish.internship.data.repository.LocalSongsRepository
import com.devshish.internship.data.repository.ProfileRepository
import com.devshish.internship.domain.repository.IAlbumsRepository
import com.devshish.internship.domain.repository.IProfileRepository
import com.devshish.internship.domain.repository.ISongsRepository
import com.devshish.internship.presentation.ui.library.likedalbums.AlbumsViewModel
import com.devshish.internship.presentation.ui.library.likedsongs.LikedSongsViewModel
import com.devshish.internship.presentation.ui.library.localsongs.LocalSongsViewModel
import com.devshish.internship.presentation.ui.profile.EditProfileViewModel
import com.devshish.internship.presentation.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val LIKED_SONGS = "liked"
private const val LOCAL_SONGS = "local"

val appModule = module {
    // Profile
    single<IProfileRepository> { ProfileRepository() }

    // Library
    single<IAlbumsRepository> { AlbumsRepository() }
    single<ISongsRepository>(named(LIKED_SONGS)) { LikedSongsRepository() }
    single<ISongsRepository>(named(LOCAL_SONGS)) {
        LocalSongsRepository(applicationContext = get())
    }
}

val viewModelModule = module {
    // Profile
    viewModel { ProfileViewModel(repository = get()) }
    viewModel { EditProfileViewModel(repository = get()) }

    // Library
    viewModel { LikedSongsViewModel(repository = get(named(LIKED_SONGS))) }
    viewModel { LocalSongsViewModel(repository = get(named(LOCAL_SONGS))) }
    viewModel { AlbumsViewModel(repository = get()) }
}
