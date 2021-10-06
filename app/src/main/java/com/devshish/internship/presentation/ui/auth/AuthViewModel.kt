package com.devshish.internship.presentation.ui.auth

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devshish.internship.domain.usecase.IAuthUseCase

class AuthViewModel(
    private val useCase: IAuthUseCase
) : ViewModel() {

    val onAuthenticateClick: LiveData<Uri>
        get() = _onAuthenticateClick
    private val _onAuthenticateClick = MutableLiveData<Uri>()

    fun requestAuthentication() {
        _onAuthenticateClick.value = useCase.authLink
    }
}
