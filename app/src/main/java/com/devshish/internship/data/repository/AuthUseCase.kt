package com.devshish.internship.data.repository

import android.net.Uri
import com.devshish.internship.BuildConfig.*
import com.devshish.internship.data.api.genius.GeniusAuthApi
import com.devshish.internship.domain.repository.ITokenRepository
import com.devshish.internship.domain.usecase.IAuthUseCase

class AuthUseCase(
    private val api: GeniusAuthApi,
    private val tokenRepository: ITokenRepository
) : IAuthUseCase {

    override val authLink: Uri
        get() = Uri.parse(BASE_URL)
            .buildUpon()
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id", CLIENT_ID)
            .appendQueryParameter("redirect_uri", "https://github.com/ArtemShishko/internship")
            .appendQueryParameter("scope", "me")
            .appendQueryParameter("state", GENIUS_REQUEST_STATE)
            .appendQueryParameter("response_type", "code")
            .build()

    override suspend fun authorize(code: String) {
        val tokenDto = api.requestToken(
            code = code,
            clientSecret = CLIENT_SECRET,
            grantType = "authorization_code",
            clientId = CLIENT_ID,
            redirectUri = "https://github.com/ArtemShishko/internship",
            responseType = "code"
        )
        tokenRepository.setToken(tokenDto.token)
    }
}
