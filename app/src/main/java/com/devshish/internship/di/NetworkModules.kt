package com.devshish.internship.di

import com.devshish.internship.BuildConfig
import com.devshish.internship.data.api.GeniusAuthApi
import com.devshish.internship.data.api.GeniusSearchApi
import com.devshish.internship.data.repository.AuthApiRepository
import com.devshish.internship.data.repository.SearchAPIRepository
import com.devshish.internship.data.repository.TokenRepository
import com.devshish.internship.domain.repository.IAuthRepository
import com.devshish.internship.domain.repository.ISearchSongsRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { TokenRepository() }

    single<ISearchSongsRepository> {
        SearchAPIRepository(
            api = get()
        )
    }

    single<IAuthRepository> {
        AuthApiRepository(
            api = get()
        )
    }

    single {
        provideAuthApiRepository(
            retrofit = get()
        )
    }

    single {
        provideSearchApiRepository(
            retrofit = get()
        )
    }

    single { provideRetrofit() }
}

fun provideRetrofit(): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun provideSearchApiRepository(retrofit: Retrofit): GeniusSearchApi =
    retrofit.create(GeniusSearchApi::class.java)

fun provideAuthApiRepository(retrofit: Retrofit): GeniusAuthApi =
    retrofit.create(GeniusAuthApi::class.java)