package com.devshish.internship.data.repository

import com.devshish.internship.domain.model.User
import com.devshish.internship.domain.repository.IProfileRepository

class ProfileRepository : IProfileRepository {

    private val user = User(
        "Arthas",
        "Belarus",
        "Minsk",
        "Not Provided",
        "https://news.cgtn.com/news/2021-01-21/Travelogue-Getting-to-know-endangered-Yunnan-snub-nosed-monkeys-XehOQcFLY4/img/44f91c3e427e46e3a01f3f693a0a11ff/44f91c3e427e46e3a01f3f693a0a11ff-1280.png",
        "Not Chosen"
    )

    override suspend fun getUser() = user
}