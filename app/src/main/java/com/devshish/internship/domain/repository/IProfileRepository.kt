package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IProfileRepository {

    fun getUser(): Flow<User>

    suspend fun editUser(updatedUser: User)
}
