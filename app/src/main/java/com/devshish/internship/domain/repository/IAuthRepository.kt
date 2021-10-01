package com.devshish.internship.domain.repository

import okhttp3.ResponseBody
import retrofit2.Response

interface IAuthRepository {

    suspend fun requestAuthentication(): Response<ResponseBody>

//    suspend fun getToken(): Response<ResponseBody>
}
