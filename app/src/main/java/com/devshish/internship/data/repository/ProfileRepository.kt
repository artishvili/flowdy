package com.devshish.internship.data.repository

import com.devshish.internship.domain.model.User
import com.devshish.internship.domain.repository.IProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ProfileRepository : IProfileRepository {

    private val user = MutableStateFlow(
        User(
            nickname = "Artem",
            country = "Belarus",
            city = "Minsk",
            description = "Profile desc",
            photo = null,
            background = null
        )
    )

    override fun getUser() = user.asStateFlow()

    override suspend fun editUser(updatedUser: User) {
        user.value = updatedUser
    }
}