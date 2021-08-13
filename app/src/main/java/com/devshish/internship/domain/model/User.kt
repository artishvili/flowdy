package com.devshish.internship.domain.model

data class User(
    val nickname: String,
    val country: String? = "Country: not chosen",
    val city: String? = "City: not chosen",
    val description: String? = "Description: not provided",
    val photo: String?,
    val background: String? = "Background: not chosen"
)
