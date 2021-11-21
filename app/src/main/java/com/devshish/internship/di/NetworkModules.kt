package com.devshish.internship.di

import com.devshish.internship.BuildConfig
import com.devshish.internship.data.api.genius.GeniusAuthApi
import com.devshish.internship.data.api.genius.GeniusProfileApi
import com.devshish.internship.data.api.genius.GeniusSearchApi
import com.devshish.internship.data.api.lastfm.LastFmChartsApi
import com.devshish.internship.di.Urls.GENIUS
import com.devshish.internship.di.Urls.LASTFM
import com.devshish.internship.domain.repository.ITokenRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

enum class Urls {
    GENIUS,
    LASTFM
}

val geniusModule = module {

    // Interceptor
    single(named(GENIUS)) {
        val tokenRepository = get<ITokenRepository>()
        Interceptor {
            val original = it.request()
            val token = if (tokenRepository.token.isNullOrBlank()) {
                ""
            } else {
                "Bearer ${tokenRepository.token}"
            }
            val request = original.newBuilder()
                .header("Authorization", token)
                .method(original.method, original.body)
                .build()
            it.proceed(request)
        }
    }

    // OkHttpClient
    single(named(GENIUS)) {
        OkHttpClient().newBuilder()
            .addInterceptor(interceptor = get(named(GENIUS)))
            .build()
    }

    // Retrofit
    single(named(GENIUS)) { provideRetrofitGenius(okHttpClient = get(named(GENIUS))) }

    // Auth API
    single { provideAuthApiRepository(retrofit = get(named(GENIUS))) }

    // Search API
    single { provideSearchApiRepository(retrofit = get(named(GENIUS))) }

    // Profile API
    single { provideProfileApiRepository(retrofit = get(named(GENIUS))) }
}

val lastFmModule = module {

    // HttpLoggingInterceptor
    single(named("aa")) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    // Interceptor
    single(named(LASTFM)) {
        Interceptor {
            val original = it.request()
            val url = original.url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.LASTFM_API_KEY)
                .addQueryParameter("format", "json")
                .addQueryParameter("limit", "10")
                .build()
            val request = original.newBuilder().url(url).build()
            it.proceed(request)
        }
    }

    // OkHttpClient
    single(named(LASTFM)) {
        OkHttpClient().newBuilder()
            .addNetworkInterceptor(interceptor = get(named(LASTFM)))
           // .addInterceptor(interceptor = get(named("aa")))
            .build()
    }

    // Retrofit
    single(named(LASTFM)) { provideRetrofitLastFm(okHttpClient = get(named(LASTFM))) }

    // Charts API
    single { provideChartsApiRepository(retrofit = get(named(LASTFM))) }
}

fun provideRetrofitGenius(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun provideRetrofitLastFm(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.LASTFM_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun provideSearchApiRepository(retrofit: Retrofit): GeniusSearchApi =
    retrofit.create(GeniusSearchApi::class.java)

fun provideAuthApiRepository(retrofit: Retrofit): GeniusAuthApi =
    retrofit.create(GeniusAuthApi::class.java)

fun provideProfileApiRepository(retrofit: Retrofit): GeniusProfileApi =
    retrofit.create(GeniusProfileApi::class.java)

fun provideChartsApiRepository(retrofit: Retrofit): LastFmChartsApi =
    retrofit.create(LastFmChartsApi::class.java)
