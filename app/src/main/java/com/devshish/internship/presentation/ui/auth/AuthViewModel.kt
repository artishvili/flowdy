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

    val requestAuth: LiveData<Uri>
        get() = _requestAuth
    private val _requestAuth = MutableLiveData<Uri>()

    fun requestAuthentication() {
        _requestAuth.value = repository.authRequest
        _onAuthenticateClick.value = Unit
    }
}
