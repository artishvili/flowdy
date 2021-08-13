package com.devshish.internship.data.repository

import com.devshish.internship.domain.model.User
import com.devshish.internship.domain.repository.ProfileFetch

class ProfileRepository : ProfileFetch {

    private val user = User("Arthas", photo = "")

    override suspend fun getUser() = user
}