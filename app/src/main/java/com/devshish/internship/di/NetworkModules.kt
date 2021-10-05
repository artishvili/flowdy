package com.devshish.internship.di

import android.content.Context
import android.content.SharedPreferences
import com.devshish.internship.BuildConfig
import com.devshish.internship.data.api.GeniusAuthApi
import com.devshish.internship.data.api.GeniusSearchApi
import com.devshish.internship.data.repository.AuthUseCase
import com.devshish.internship.data.repository.SearchAPIRepository
import com.devshish.internship.data.repository.TokenRepositoryImpl
import com.devshish.internship.data.utils.Constants
import com.devshish.internship.domain.repository.IAuthRepository
import com.devshish.internship.domain.repository.ISearchSongsRepository
import com.devshish.internship.domain.repository.ITokenRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
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

    single<IAuthRepository> {
        AuthUseCase(
            api = get(),
            tokenRepository = get()
        )
    }

    single {
        provideSharedPrefs(
            context = get()
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
        provideOkHttpClient(
            interceptor = get()
        )
    }

    single {
        provideInterceptor(
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

fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient =
    OkHttpClient().newBuilder()
        .addInterceptor(interceptor)
        .build()

fun provideInterceptor(
    tokenRepository: ITokenRepository
): Interceptor =
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

fun provideSharedPrefs(context: Context): SharedPreferences =
    context.getSharedPreferences(Constants.USER_TOKEN, Context.MODE_PRIVATE)

fun provideSearchApiRepository(retrofit: Retrofit): GeniusSearchApi =
    retrofit.create(GeniusSearchApi::class.java)

fun provideAuthApiRepository(retrofit: Retrofit): GeniusAuthApi =
    retrofit.create(GeniusAuthApi::class.java)