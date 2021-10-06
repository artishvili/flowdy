package com.devshish.internship.presentation.ui.web

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.usecase.IAuthUseCase
import kotlinx.coroutines.launch

class WebViewModel(
    private val useCase: IAuthUseCase,
) : ViewModel() {

    val navigateForward: LiveData<Unit>
        get() = _navigateForward
    private val _navigateForward = MutableLiveData<Unit>()

    fun inputCode(code: String) {
        viewModelScope.launch {
            useCase.authorize(code)
            _navigateForward.value = Unit
        }
    }
}
