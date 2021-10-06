package com.devshish.internship.presentation.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devshish.internship.domain.repository.ITokenRepository

class SplashViewModel(
    private val repository: ITokenRepository
) : ViewModel() {

    val navigateToAuth: LiveData<Unit>
        get() = _navigateToAuth
    private val _navigateToAuth = MutableLiveData<Unit>()

    val navigateToApp: LiveData<Unit>
        get() = _navigateToApp
    private val _navigateToApp = MutableLiveData<Unit>()

    init {
        decideNavigation()
    }

    private fun decideNavigation() {
        if (repository.token.isNullOrBlank()) {
            _navigateToAuth.value = Unit
        } else {
            _navigateToApp.value = Unit
        }
    }
}
