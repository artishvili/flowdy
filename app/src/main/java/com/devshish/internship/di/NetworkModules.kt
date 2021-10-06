package com.devshish.internship.di

import android.content.Context
import com.devshish.internship.BuildConfig
import com.devshish.internship.data.api.GeniusAuthApi
import com.devshish.internship.data.api.GeniusSearchApi
import com.devshish.internship.data.repository.AuthUseCase
import com.devshish.internship.data.repository.SearchAPIRepository
import com.devshish.internship.data.repository.TokenRepositoryImpl
import com.devshish.internship.domain.usecase.IAuthUseCase
import com.devshish.internship.domain.repository.ISearchSongsRepository
import com.devshish.internship.domain.repository.ITokenRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

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

    single {
        provideRetrofit(
            okHttpClient = get()
        )
    }

    single {
        val tokenRepository = get<ITokenRepository>()
        Interceptor {
            val original = it.request()
            val token = if (tokenRepository.token?.isNotEmpty() == true) {
                "Bearer ${tokenRepository.token}"
            } else {
                ""
            }
            val request = original.newBuilder()
                .header("Authorization", token)
                .method(original.method, original.body)
                .build()
            it.proceed(request)
        }
    }

    single {
        OkHttpClient().newBuilder()
            .addInterceptor(interceptor = get())
            .build()
    }

    single {
        androidApplication().applicationContext
            .getSharedPreferences(
                "com.devshish.internship.PREFERENCE_FILE_KEY",
                Context.MODE_PRIVATE
            )
    }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun provideSearchApiRepository(retrofit: Retrofit): GeniusSearchApi =
    retrofit.create(GeniusSearchApi::class.java)

fun provideAuthApiRepository(retrofit: Retrofit): GeniusAuthApi =
    retrofit.create(GeniusAuthApi::class.java)
