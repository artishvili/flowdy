package com.devshish.internship

import com.devshish.internship.data.repository.ProfileRepository
import com.devshish.internship.data.repository.SongsRepository
import com.devshish.internship.domain.repository.IProfileRepository
import com.devshish.internship.presentation.ui.likedsongs.LikedSongsViewModel
import com.devshish.internship.presentation.ui.profile.EditProfileViewModel
import com.devshish.internship.presentation.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<IProfileRepository> { ProfileRepository() }
    single { SongsRepository() }
}

val viewModelModule = module {
    viewModel { ProfileViewModel(repository = get()) }
    viewModel { EditProfileViewModel(repository = get()) }
    viewModel { LikedSongsViewModel(repository = get()) }
}