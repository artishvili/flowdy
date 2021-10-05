package com.devshish.internship.presentation.ui.auth

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devshish.internship.domain.repository.IAuthRepository

class AuthViewModel(
    private val repository: IAuthRepository
) : ViewModel() {

    val onAuthenticateClick: LiveData<Unit>
        get() = _onAuthenticateClick
    private val _onAuthenticateClick = MutableLiveData<Unit>()

    val authLink: LiveData<Uri>
        get() = _authLink
    private val _authLink = MutableLiveData<Uri>()

    fun requestAuthentication() {
        _authLink.value = repository.authLink
        _onAuthenticateClick.value = Unit
    }
}
