package com.devshish.internship.data.model.dto

import com.google.gson.annotations.SerializedName

data class UserDTO(
    val response: UserResponseDTO
)

data class UserResponseDTO(
    val user: UserInfoDTO
)

data class UserInfoDTO(
    @SerializedName("header_image_url")
    val headerImageUrl: String,
    @SerializedName("photo_url")
    val photoUrl: String,
    val email: String,
    val name: String
)
