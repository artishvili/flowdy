package com.devshish.internship.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class EditProfileViewModel : ViewModel() {

    private val _navigateBackEvent = MutableSharedFlow<Unit>(0)
    val navigateBackEvent: SharedFlow<Unit> = _navigateBackEvent

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
            Timber.d(user.toString())
            delay(1000)
            _navigateBackEvent.emit(Unit)
        }
    }
}