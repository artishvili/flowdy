package com.devshish.internship.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.User
import com.devshish.internship.domain.repository.IProfileRepository
import com.devshish.internship.domain.repository.ITokenRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    repository: IProfileRepository,
    private val tokenRepository: ITokenRepository
) : ViewModel() {

    val userData: LiveData<User>
        get() = _userData
    private val _userData = MutableLiveData<User>()

    val navigationEvent: LiveData<Unit>
        get() = _navigationEvent
    private val _navigationEvent = MutableLiveData<Unit>()

    init {
        viewModelScope.launch {
            _userData.value = repository.getUser()
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenRepository.clear()
            if (tokenRepository.token == null) {
                _navigationEvent.value = Unit
            }
        }
    }
}
