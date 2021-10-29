package com.devshish.internship.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.User
import com.devshish.internship.domain.repository.IProfileRepository
import com.devshish.internship.domain.repository.ITokenRepository
import com.devshish.internship.presentation.ui.utils.Event
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

    val dialogEvent: LiveData<Unit>
        get() = _dialogEvent
    private val _dialogEvent = MutableLiveData<Unit>()

    init {
        viewModelScope.launch {
            _userData.value = repository.getUser()
        }
    }

    fun onLogoutClick() {
        _dialogEvent.value = Unit
    }

    fun onDialogConfirmation() {
        _navigationEvent.value = Unit
        viewModelScope.launch {
            tokenRepository.clear()
        }
    }
}
