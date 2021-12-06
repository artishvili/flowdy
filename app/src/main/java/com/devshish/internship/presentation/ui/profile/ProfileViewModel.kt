package com.devshish.internship.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.User
import com.devshish.internship.domain.repository.IProfileRepository
import com.devshish.internship.domain.repository.ITokenRepository
import com.devshish.internship.presentation.ui.MainViewModel
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: IProfileRepository,
    private val tokenRepository: ITokenRepository,
    private val mainViewModel: MainViewModel
) : ViewModel() {

    val userData: LiveData<User>
        get() = _userData
    private val _userData = MutableLiveData<User>()

    val navigationEvent: LiveData<Unit>
        get() = _navigationEvent
    private val _navigationEvent = MutableLiveData<Unit>()

    val noInternetConnection: LiveData<Unit>
        get() = _noInternetConnection
    private val _noInternetConnection = MutableLiveData<Unit>()

    init {
        loadUser()
    }

    fun loadUser() {
        if (mainViewModel.checkInternetConnection()) {
            viewModelScope.launch {
                _userData.value = repository.getUser()
            }
        } else {
            _noInternetConnection.value = Unit
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenRepository.clear()
            _navigationEvent.value = Unit
        }
    }
}
