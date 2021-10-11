package com.devshish.internship.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.User
import com.devshish.internship.domain.repository.IProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileViewModel(
    repository: IProfileRepository
) : ViewModel() {

    val userData: LiveData<User>
        get() = _userData
    private val _userData = MutableLiveData<User>()

    init {
        viewModelScope.launch {
            _userData.value = repository.getUser()
        }
    }
}
