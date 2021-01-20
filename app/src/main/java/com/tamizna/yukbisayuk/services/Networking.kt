package com.tamizna.yukbisayuk.services

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Networking {
    val service: NetworkService by lazy {
        Retrofit.Builder().baseUrl("http://api.skydu.cloud:8000/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .apply {
                                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                                else HttpLoggingInterceptor.Level.NONE
                            })
                    .readTimeout(60.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(60.toLong(), TimeUnit.SECONDS)
                    .build()).addConverterFactory(GsonConverterFactory.create()).build()
            .create(NetworkService::class.java)
    }
}