package com.devshish.internship.domain.model

import android.net.Uri

data class User(
    val nickname: String,
    val country: String?,
    val city: String?,
    val description: String?,
    val photo: Uri?,
    val background: Uri?
)
