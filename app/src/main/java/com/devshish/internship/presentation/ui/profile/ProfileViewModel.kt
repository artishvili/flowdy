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
import timber.log.Timber

class ProfileViewModel(
    private val repository: IProfileRepository,
    private val tokenRepository: ITokenRepository
) : ViewModel() {

    val userData: LiveData<User>
        get() = _userData
    private val _userData = MutableLiveData<User>()

    val event: LiveData<Event<Boolean>>
        get() = _event
    private val _event = MutableLiveData<Event<Boolean>>()

    init {
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch {
            try {
                _userData.value = repository.getUser()
            } catch (e: Exception) {
                _event.value = Event(false)
                Timber.e(e)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenRepository.clear()
            _event.value = Event(true)
        }
    }
}
