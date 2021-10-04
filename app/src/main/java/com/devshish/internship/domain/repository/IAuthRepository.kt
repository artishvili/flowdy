package com.devshish.internship.domain.repository

import android.net.Uri
import com.devshish.internship.data.model.TokenDTO

interface IAuthRepository {

    val authRequest: Uri

    suspend fun getToken(code: String): TokenDTO
}
