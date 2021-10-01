package com.devshish.internship.presentation.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.repository.IAuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    authApiRepository: IAuthRepository
) : ViewModel() {

    val requestAuth: LiveData<String>
        get() = _requestAuth
    private val _requestAuth = MutableLiveData<String>()

    val requestToken: LiveData<String>
        get() = _requestToken
    private val _requestToken = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            _requestAuth.value = authApiRepository.requestAuthentication().raw().request.url.toString()
//            _requestToken.value = authApiRepository.getToken().toString()
        }
    }
}
