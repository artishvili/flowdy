package com.devshish.internship.presentation.ui.web

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.data.repository.TokenRepository
import com.devshish.internship.domain.repository.IAuthRepository
import kotlinx.coroutines.launch

class WebViewModel(
    private val authRepository: IAuthRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    fun getToken(code: String) {
        viewModelScope.launch {
            val response = authRepository.getToken(code)
            tokenRepository.token = response.token
        }
    }
}
