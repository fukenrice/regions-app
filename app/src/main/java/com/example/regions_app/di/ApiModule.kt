package com.example.regions_app.di

import com.example.regions_app.data.api.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val gson = GsonConverterFactory.create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://vmeste.wildberries.ru/api/guide-service/v1/")
            .client(client)
            .addConverterFactory(gson)
            .build()
        return retrofit
    }

    @Provides
    fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}