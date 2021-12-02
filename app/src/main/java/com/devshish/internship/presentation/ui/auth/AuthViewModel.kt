package com.devshish.internship.presentation.ui.auth

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devshish.internship.domain.usecase.IAuthUseCase
import com.devshish.internship.presentation.ui.utils.Event

class AuthViewModel(
    private val useCase: IAuthUseCase
) : ViewModel() {

    val onAuthenticateClick: LiveData<Event<Uri>>
        get() = _onAuthenticateClick
    private val _onAuthenticateClick = MutableLiveData<Event<Uri>>()

    fun requestAuthentication() {
        _onAuthenticateClick.value = Event(useCase.authLink)
    }
}
