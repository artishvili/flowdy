package com.devshish.internship.data.repository

import com.devshish.internship.domain.model.User
import com.devshish.internship.domain.repository.IProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ProfileRepository : IProfileRepository {

    private var user = MutableStateFlow(User(
        nickname = "Artem",
        country = "Belarus",
        city = "Minsk",
        description = null,
        photo = "https://news.cgtn.com/news/2021-01-21/Travelogue-Getting-to-know-endangered-Yunnan-snub-nosed-monkeys-XehOQcFLY4/img/44f91c3e427e46e3a01f3f693a0a11ff/44f91c3e427e46e3a01f3f693a0a11ff-1280.png",
        background = null
    ))

    override fun getUser() = user.asStateFlow()

    override suspend fun editUser(updatedUser: User) {
        user.value = updatedUser
    }
}