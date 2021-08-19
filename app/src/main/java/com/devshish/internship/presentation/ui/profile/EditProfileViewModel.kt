package com.devshish.internship.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class EditProfileViewModel : ViewModel() {

    val navigateBackEvent: LiveData<Unit>
        get() = _navigateBackEvent
    private val _navigateBackEvent = MutableLiveData<Unit>()

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
            _navigateBackEvent.value = Unit
        }
    }
}