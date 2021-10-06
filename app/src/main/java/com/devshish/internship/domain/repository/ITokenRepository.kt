package com.devshish.internship.domain.repository

interface ITokenRepository {

    val token: String?

    suspend fun setToken(token: String)

    suspend fun clear()
}
