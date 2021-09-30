package com.devshish.internship.di

import com.devshish.internship.BuildConfig
import com.devshish.internship.data.repository.SearchAPIRepository
import com.devshish.internship.data.api.GeniusSearchApi
import com.devshish.internship.domain.repository.ISearchSongsRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<ISearchSongsRepository> {
        SearchAPIRepository(
            api = get()
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