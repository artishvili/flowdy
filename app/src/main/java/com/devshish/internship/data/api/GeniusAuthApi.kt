package com.devshish.internship.data.api

import com.devshish.internship.data.model.TokenDTO
import retrofit2.http.POST
import retrofit2.http.Query

interface GeniusAuthApi {

    @POST("oauth/token")
    suspend fun requestToken(
        @Query("code") code: String,
        @Query("client_secret") clientSecret: String,
        @Query("grant_type") grantType: String,
        @Query("client_id") clientId: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("response_type") responseType: String
    ): TokenDTO
}
