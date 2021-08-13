package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.User

interface ProfileFetch {

    suspend fun getUser(): User
}