package com.devshish.internship.data.repository

import android.net.Uri
import com.devshish.internship.BuildConfig.BASE_URL
import com.devshish.internship.data.api.GeniusAuthApi
import com.devshish.internship.domain.repository.IAuthRepository
import okhttp3.ResponseBody
import retrofit2.Response

const val CLIENT_ID = "dhj7OPXTEwXBwXYgw1Lrm3fkZ6MncRCZptfyj_lGaOMaORZ8UWG8esomSkZzfI3P"
const val REDIRECT_URI = "dhj7OPXTEwXBwXYgw1Lrm3fkZ6MncRCZptfyj_lGaOMaORZ8UWG8esomSkZzfI3P"
const val SCOPE = "me"
const val STATE = "REQUESTING"
const val RESPONSE_TYPE = "code"

class AuthApiRepository(
    private val api: GeniusAuthApi
) : IAuthRepository {

    override suspend fun requestAuthentication(): Response<ResponseBody> =
        api.requestAuthentication(
            clientId = CLIENT_ID,
            redirectUri = "https://httpbin.org/anything",
            scope = SCOPE,
            state = STATE,
            responseType = RESPONSE_TYPE
        )

    fun request(): Uri = Uri.parse(BASE_URL)
        .buildUpon()
        .appendPath("oauth")
        .appendPath("authorize")
        .appendQueryParameter("client_id", CLIENT_ID)
        .appendQueryParameter("redirect_uri", "")
        .appendQueryParameter("scope", "me")
        .appendQueryParameter("state", "REQUESTING")
        .appendQueryParameter("response_type", "code")
        .build()
}
