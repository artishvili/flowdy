package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.User

interface IProfileRepository {

    suspend fun getUser(): User
}
