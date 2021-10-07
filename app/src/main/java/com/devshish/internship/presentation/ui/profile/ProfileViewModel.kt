package com.devshish.internship.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.data.model.UserInfoDTO
import com.devshish.internship.domain.model.User
import com.devshish.internship.domain.repository.IProfileRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileViewModel(
    repository: IProfileRepository
) : ViewModel() {

    val userData: LiveData<UserInfoDTO>
        get() = _userData
    private val _userData = MutableLiveData<UserInfoDTO>()

    init {
        viewModelScope.launch {
            _userData.value = repository.getUser()
        }
    }

    private val _navigateForwardEvent = MutableSharedFlow<Unit>()
    val navigateForwardEvent: Flow<Unit> = _navigateForwardEvent.asSharedFlow()

    fun onEditButtonClick() {
        viewModelScope.launch {
            Timber.d(userData.value.toString())
            _navigateForwardEvent.emit(Unit)
        }
    }
}
