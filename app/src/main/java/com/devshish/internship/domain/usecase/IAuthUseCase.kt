package com.devshish.internship.domain.usecase

import android.net.Uri

interface IAuthUseCase {

    val authLink: Uri

    suspend fun authorize(code: String)
}
