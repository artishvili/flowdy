package com.devshish.internship.presentation.ui.profile

import android.net.Uri
import androidx.core.net.toUri
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

    private val _profileImageFlow = MutableStateFlow(Uri.EMPTY)
    val profileImageFlow: Flow<Uri?> = _profileImageFlow.asStateFlow()

    private val _backgroundImageFlow = MutableStateFlow(Uri.EMPTY)
    val backgroundImageFlow: Flow<Uri?> = _backgroundImageFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _profileImageFlow.value = userFlow.first().photo?.toUri()
            _backgroundImageFlow.value = userFlow.first().background?.toUri()
        }
    }

    fun onProfileImagePick(uri: Uri) {
        _profileImageFlow.value = uri
    }

    fun onBackgroundImagePick(uri: Uri) {
        _backgroundImageFlow.value = uri
    }

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
                photo = _profileImageFlow.value.toString(),
                background = _backgroundImageFlow.value.toString()
            )
            repository.editUser(user)
            Timber.d(userFlow.toString())
            _navigateBackEvent.emit(Unit)
        }
    }
}
