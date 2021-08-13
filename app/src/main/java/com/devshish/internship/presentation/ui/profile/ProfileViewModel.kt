package com.devshish.internship.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.data.repository.ProfileRepository
import com.devshish.internship.domain.model.User
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    val user: LiveData<User>
        get() = _user
    private val _user = MutableLiveData<User>()

    init {
        viewModelScope.launch {
            _user.value = repository.getUser()
        }
    }
}