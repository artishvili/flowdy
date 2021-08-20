package com.devshish.internship.presentation.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.User
import com.devshish.internship.domain.repository.IProfileRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class EditProfileViewModel(
    private val repository: IProfileRepository
) : ViewModel() {

    val userFlow: Flow<User> = repository.getUser()

    private val _navigateBackEvent = MutableSharedFlow<Unit>()
    val navigateBackEvent: Flow<Unit> = _navigateBackEvent.asSharedFlow()

    private val _profileImageFlow = MutableStateFlow("")
    val profileImageFlow: Flow<String> = _profileImageFlow.asStateFlow()

    private val _backgroundImageFlow = MutableStateFlow("")
    val backgroundImageFlow: Flow<String> = _backgroundImageFlow.asStateFlow()

    fun onProfileImagePick(uri: Uri?) {
        _profileImageFlow.value = uri.toString()
    }

    fun onBackgroundImagePick(uri: Uri?) {
        _backgroundImageFlow.value = uri.toString()
    }

    fun onSaveButtonClick(
        nickname: String,
        country: String?,
        city: String?,
        description: String?
    ) {
        viewModelScope.launch {
            val user = User(
                nickname = nickname,
                country = country,
                city = city,
                description = description,
                photo = profileImageFlow.first(),
                background = backgroundImageFlow.first()
            )
            repository.editUser(user)
            Timber.d(userFlow.toString())
            _navigateBackEvent.emit(Unit)
        }
    }
}
