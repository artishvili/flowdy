package com.devshish.internship

import com.devshish.internship.data.repository.ProfileRepository
import com.devshish.internship.data.repository.SongsRepository
import com.devshish.internship.domain.repository.IProfileRepository
import com.devshish.internship.presentation.ui.likedsongs.LikedSongsViewModel
import com.devshish.internship.presentation.ui.likedsongs.LikedSongsViewModelFactory
import com.devshish.internship.presentation.ui.profile.EditProfileViewModel
import com.devshish.internship.presentation.ui.profile.EditProfileViewModelFactory
import com.devshish.internship.presentation.ui.profile.ProfileViewModel
import com.devshish.internship.presentation.ui.profile.ProfileViewModelFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<IProfileRepository> { ProfileRepository() }
    factory { ProfileViewModelFactory(get()) }
    factory { EditProfileViewModelFactory(get()) }

    single { SongsRepository() }
    factory { LikedSongsViewModelFactory(get()) }
}

val viewModelModule = module {
    viewModel { ProfileViewModel(get()) }
    viewModel { EditProfileViewModel(get()) }

    viewModel { LikedSongsViewModel(get()) }
}