package com.devshish.internship

import com.devshish.internship.data.repository.AlbumsRepository
import com.devshish.internship.data.repository.LocalSongsRepository
import com.devshish.internship.data.repository.ProfileRepository
import com.devshish.internship.data.repository.SongsRepository
import com.devshish.internship.domain.repository.IAlbumsRepository
import com.devshish.internship.domain.repository.ILocalSongsRepository
import com.devshish.internship.domain.repository.IProfileRepository
import com.devshish.internship.domain.repository.LikedSongsRepository
import com.devshish.internship.presentation.ui.library.likedalbums.AlbumsViewModel
import com.devshish.internship.presentation.ui.library.likedsongs.LikedSongsViewModel
import com.devshish.internship.presentation.ui.library.localsongs.LocalSongsViewModel
import com.devshish.internship.presentation.ui.profile.EditProfileViewModel
import com.devshish.internship.presentation.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<IProfileRepository> { ProfileRepository() }
    single<LikedSongsRepository> { SongsRepository() }
    single<ILocalSongsRepository> { LocalSongsRepository(applicationContext = get()) }
    single<IAlbumsRepository> { AlbumsRepository() }
}

val viewModelModule = module {
    viewModel { ProfileViewModel(repository = get()) }
    viewModel { EditProfileViewModel(repository = get()) }
    viewModel { LikedSongsViewModel(repository = get()) }
    viewModel { LocalSongsViewModel(repository = get()) }
    viewModel { AlbumsViewModel(repository = get()) }
}