package com.devshish.internship.data.model.dto

import com.google.gson.annotations.SerializedName

data class TokenDTO(
    @SerializedName("access_token")
    val token: String
)
