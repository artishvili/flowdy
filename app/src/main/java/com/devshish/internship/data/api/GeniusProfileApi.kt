package com.devshish.internship.data.api

import com.devshish.internship.data.model.dto.UserDTO
import retrofit2.http.GET

interface GeniusProfileApi {

    @GET("account")
    suspend fun getUser(): UserDTO
}
