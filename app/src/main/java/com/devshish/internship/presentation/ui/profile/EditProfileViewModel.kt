package com.devshish.internship.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.User
import com.devshish.internship.domain.repository.IProfileRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class EditProfileViewModel(
    private val repository: IProfileRepository
) : ViewModel() {

    val userFlow = repository.getUser()

    private val _navigateBackEvent = MutableSharedFlow<Unit>()
    val navigateBackEvent: Flow<Unit> = _navigateBackEvent.asSharedFlow()

    fun onSaveButtonClick(
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
            repository.editUser(user)
            Timber.d(user.toString())
            delay(1000)
            _navigateBackEvent.emit(Unit)
        }
    }
}