package com.devshish.internship.di

import android.content.Context
import com.devshish.internship.BuildConfig
import com.devshish.internship.data.api.genius.GeniusAuthApi
import com.devshish.internship.data.api.genius.GeniusProfileApi
import com.devshish.internship.data.api.genius.GeniusSearchApi
import com.devshish.internship.data.api.lastfm.LastFmChartsApi
import com.devshish.internship.data.repository.AuthUseCase
import com.devshish.internship.data.repository.SearchAPIRepository
import com.devshish.internship.data.repository.TokenRepositoryImpl
import com.devshish.internship.di.Urls.*
import com.devshish.internship.domain.usecase.IAuthUseCase
import com.devshish.internship.domain.repository.ISearchSongsRepository
import com.devshish.internship.domain.repository.ITokenRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

enum class Urls {
    GENIUS,
    LASTFM
}

val networkModule = module {

    single(named(LASTFM)) {
        provideRetrofit(
            okHttpClient = get(named(LASTFM)),
            gson = get()
        )
    }

    single(named(GENIUS)) {
        provideRetrofit(
            okHttpClient = get(named(GENIUS))
        )
    }

    single(named(LASTFM)) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
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

    single(named(GENIUS)) {
        OkHttpClient().newBuilder()
            .addInterceptor(interceptor = get(named(GENIUS)))
            .build()
    }

    single(named(LASTFM)) {
        OkHttpClient().newBuilder()
            .addInterceptor(interceptor = get(named(LASTFM)))
            .build()
    }

    single {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single {
        provideAuthApiRepository(
            retrofit = get(named(GENIUS))
        )
    }

    single {
        provideSearchApiRepository(
            retrofit = get(named(GENIUS))
        )
    }

    single {
        provideProfileApiRepository(
            retrofit = get(named(GENIUS))
        )
    }

    single {
        provideArtistApiRepository(
            retrofit = get(named(LASTFM))
        )
    }

    single {
        androidApplication().applicationContext
            .getSharedPreferences(
                "com.devshish.internship.PREFERENCE_FILE_KEY",
                Context.MODE_PRIVATE
            )
    }

    single<ITokenRepository> {
        TokenRepositoryImpl(
            sharedPref = get()
        )
    }

    single<ISearchSongsRepository> {
        SearchAPIRepository(
            api = get()
        )
    }

    single<IAuthUseCase> {
        AuthUseCase(
            api = get(),
            tokenRepository = get()
        )
    }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.LASTFM_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

fun provideSearchApiRepository(retrofit: Retrofit): GeniusSearchApi =
    retrofit.create(GeniusSearchApi::class.java)

fun provideAuthApiRepository(retrofit: Retrofit): GeniusAuthApi =
    retrofit.create(GeniusAuthApi::class.java)

fun provideProfileApiRepository(retrofit: Retrofit): GeniusProfileApi =
    retrofit.create(GeniusProfileApi::class.java)

fun provideArtistApiRepository(retrofit: Retrofit): LastFmChartsApi =
    retrofit.create(LastFmChartsApi::class.java)
