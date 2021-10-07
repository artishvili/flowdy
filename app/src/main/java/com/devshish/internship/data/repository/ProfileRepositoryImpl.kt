package com.devshish.internship.data.repository

import com.devshish.internship.data.api.GeniusProfileApi
import com.devshish.internship.data.model.UserInfoDTO
import com.devshish.internship.domain.repository.IProfileRepository

class ProfileRepositoryImpl(
    private val api: GeniusProfileApi
) : IProfileRepository {

    override suspend fun getUser(): UserInfoDTO =
        api.getUser().response.user
}
