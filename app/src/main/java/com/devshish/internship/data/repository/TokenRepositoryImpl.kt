package com.devshish.internship.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.devshish.internship.domain.repository.ITokenRepository

class TokenRepositoryImpl(
    private val sharedPref: SharedPreferences
) : ITokenRepository {

    companion object {
        private const val USER_TOKEN = "user_token"
    }

    override val token: String?
        get() = sharedPref.getString(USER_TOKEN, null)

    override suspend fun setToken(token: String) {
        sharedPref.edit(commit = true) {
            putString(USER_TOKEN, token)
        }
    }

    override suspend fun clear() {
        sharedPref.edit(commit = true) {
            remove(USER_TOKEN)
        }
    }
}
