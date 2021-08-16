package com.devshish.internship.data.repository

import com.devshish.internship.domain.model.User
import com.devshish.internship.domain.repository.IProfileRepository

class ProfileRepository : IProfileRepository {

    private val user = User(
        "Arthas",
        "Country: Belarus",
        "City: Minsk",
        "Description: Not Provided",
        "Photo: Not Chosen",
        "Background: Not Chosen"
    )

    override suspend fun getUser() = user
}