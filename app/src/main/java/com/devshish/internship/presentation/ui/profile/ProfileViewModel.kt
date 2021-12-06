package com.devshish.internship.presentation.ui.profile

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.R
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

    sealed class UIState {
        data class Exception(@StringRes val messageRes: Int) : UIState()
        object NoException : UIState()
    }

    val userData: LiveData<User>
        get() = _userData
    private val _userData = MutableLiveData<User>()

    val uiState: LiveData<Event<UIState>>
        get() = _uiState
    private val _uiState = MutableLiveData<Event<UIState>>()

    init {
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch {
            try {
                _userData.value = repository.getUser()
            } catch (e: Exception) {
                _uiState.value = Event(UIState.Exception(R.string.snackbar_something_went_wrong))
                Timber.e(e)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenRepository.clear()
            _uiState.value = Event(UIState.NoException)
        }
    }
}
