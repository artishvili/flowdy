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

    val userFlow: Flow<User>
        get() = _userFlow.asSharedFlow()
    private val _userFlow = MutableSharedFlow<User>(1)

    private val _navigateBackEvent = MutableSharedFlow<Unit>()
    val navigateBackEvent: Flow<Unit> = _navigateBackEvent.asSharedFlow()

    val profilePictureUri: Flow<Uri?>
        get() = _profilePictureUri.asStateFlow()
    private val _profilePictureUri = MutableStateFlow<Uri?>(null)

    val backgroundPictureUri: Flow<Uri?>
        get() = _backgroundPictureUri.asStateFlow()
    private val _backgroundPictureUri = MutableStateFlow<Uri?>(null)

    val showSnackBarEvent: Flow<Unit>
        get() = _showSnackBarEvent.asSharedFlow()
    private val _showSnackBarEvent = MutableSharedFlow<Unit>()

    init {
        viewModelScope.launch {
            repository.getUser().collect {
                _userFlow.emit(it)
                _profilePictureUri.value = it.photo
                _backgroundPictureUri.value = it.background
            }
        }
    }

    fun onProfileImagePick(uri: Uri) {
        _profilePictureUri.value = uri
    }

    fun onBackgroundImagePick(uri: Uri) {
        _backgroundPictureUri.value = uri
    }

    fun onSaveButtonClick(
        nickname: String,
        country: String,
        city: String,
        description: String
    ) {
        viewModelScope.launch {
            if (validate(nickname)) {
                val user = User(
                    nickname = nickname,
                    country = country.validateMandatoryField(),
                    city = city.validateMandatoryField(),
                    description = description.validateMandatoryField(),
                    photo = _profilePictureUri.value,
                    background = _backgroundPictureUri.value
                )

                repository.editUser(user)
                Timber.d(userFlow.toString())
                _navigateBackEvent.emit(Unit)
            } else {
                _showSnackBarEvent.emit(Unit)
            }
        }
    }

    private fun validate(nickname: String): Boolean = nickname.isNotBlank()

    private fun String.validateMandatoryField(): String? =
        if (this.isNotBlank()) this else null
}
