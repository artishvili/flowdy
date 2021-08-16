package com.devshish.internship.domain.model

data class User(
    val nickname: String,
    val country: String?,
    val city: String?,
    val description: String?,
    val photo: String?,
    val background: String?
)
