package com.devshish.internship.domain.repository

import com.devshish.internship.data.model.UserInfoDTO

interface IProfileRepository {

    suspend fun getUser(): UserInfoDTO
}
