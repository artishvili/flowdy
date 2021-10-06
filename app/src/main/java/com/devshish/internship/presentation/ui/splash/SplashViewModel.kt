package com.devshish.internship.presentation.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devshish.internship.domain.repository.ITokenRepository

class SplashViewModel(
    private val repository: ITokenRepository
) : ViewModel() {

    val navigationEvent: LiveData<SplashNavigationEvent>
        get() = _navigationEvent
    private val _navigationEvent = MutableLiveData<SplashNavigationEvent>()

    init {
        decideNavigation()
    }

    private fun decideNavigation() {
        _navigationEvent.value = if (repository.token.isNullOrBlank()) {
            SplashNavigationEvent.NAVIGATE_TO_AUTH
        } else {
            SplashNavigationEvent.NAVIGATE_TO_APP
        }
    }

    enum class SplashNavigationEvent {
        NAVIGATE_TO_AUTH,
        NAVIGATE_TO_APP
    }
}
