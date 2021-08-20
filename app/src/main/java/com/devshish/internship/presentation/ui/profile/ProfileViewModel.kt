package com.devshish.internship.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.User
import com.devshish.internship.domain.repository.IProfileRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileViewModel(
    repository: IProfileRepository
) : ViewModel() {

    private val _user = repository.getUser()
    val user: Flow<User> = _user

    private val _navigateForwardEvent = MutableSharedFlow<Unit>()
    val navigateForwardEvent: Flow<Unit> = _navigateForwardEvent.asSharedFlow()

    fun onEditButtonClick(
        nickname: String,
        country: String,
        city: String,
        description: String
    ) {
        viewModelScope.launch {
            val user = User(
                nickname = nickname,
                country = country,
                city = city,
                description = description,
                photo = null,
                background = null
            )
            Timber.d(user.toString())
            delay(1000)
            _navigateForwardEvent.emit(Unit)
        }
    }
}
