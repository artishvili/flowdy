package com.devshish.internship.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GeniusAuthApi {

    @GET("oauth/authorize")
    suspend fun requestAuthentication(
        @Query("client_id") clientId: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("scope") scope: String,
        @Query("state") state: String,
        @Query("response_type") responseType: String,
    ): Response<ResponseBody>

    @POST("oauth/token")
    suspend fun requestToken(
        @Query("code") code: String,
        @Query("client_secret") clientSecret: String,
        @Query("grant_type") grantType: String,
        @Query("client_id") clientId: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("response_type") responseType: String
    ): Response<ResponseBody>
}
