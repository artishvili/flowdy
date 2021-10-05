package com.devshish.internship.domain.repository

import android.net.Uri
import com.devshish.internship.data.model.TokenDTO

interface IAuthRepository {

    val authLink: Uri

    suspend fun authorize(code: String)
}
