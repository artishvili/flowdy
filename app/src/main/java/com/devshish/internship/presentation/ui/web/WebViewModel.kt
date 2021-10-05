package com.devshish.internship.presentation.ui.web

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.repository.IAuthRepository
import kotlinx.coroutines.launch

class WebViewModel(
    private val authRepository: IAuthRepository,
) : ViewModel() {

    val navigateForward: LiveData<Unit>
        get() = _navigateForward
    private val _navigateForward = MutableLiveData<Unit>()

    fun checkUrl(url: Uri) {
        url.getQueryParameter("code")?.let { code ->
            getToken(code)
        }
    }

    private fun getToken(code: String) {
        viewModelScope.launch {
            authRepository.authorize(code)
            _navigateForward.value = Unit
        }
    }
}
