package com.devshish.internship.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.repository.IProfileRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileViewModel(
    repository: IProfileRepository
) : ViewModel() {

    val userFlow = repository.getUser()

    private val _navigateForwardEvent = MutableSharedFlow<Unit>()
    val navigateForwardEvent: Flow<Unit> = _navigateForwardEvent.asSharedFlow()

    fun onEditButtonClick() {
        viewModelScope.launch {
            Timber.d(userFlow.toString())
            delay(1000)
            _navigateForwardEvent.emit(Unit)
        }
    }
}
