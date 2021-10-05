package com.devshish.internship.data.repository

import android.content.SharedPreferences
import com.devshish.internship.data.utils.Constants
import com.devshish.internship.domain.repository.ITokenRepository

class TokenRepositoryImpl(
    private val sharedPref: SharedPreferences
) : ITokenRepository {

    override val token: String? = null
        get() = sharedPref.getString(Constants.USER_TOKEN, field)

    override suspend fun setToken(token: String) {
        sharedPref.edit()
            .putString(Constants.USER_TOKEN, token)
            .apply()
    }

    override suspend fun clear() {
        sharedPref.edit()
            .clear()
            .apply()
    }
}
