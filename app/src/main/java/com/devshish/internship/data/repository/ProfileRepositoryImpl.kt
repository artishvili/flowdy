package com.devshish.internship.data.repository

import com.devshish.internship.data.api.GeniusProfileApi
import com.devshish.internship.domain.model.User
import com.devshish.internship.domain.repository.IProfileRepository

class ProfileRepositoryImpl(
    private val api: GeniusProfileApi
) : IProfileRepository {

    override suspend fun getUser(): User {
        val user = api.getUser().response.user
        return User(
            nickname = user.name,
            photo = user.photoUrl,
            background = user.headerImageUrl
        )
    }
}
