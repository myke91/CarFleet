package com.wundermobility.test.carfleet.api

import com.wundermobility.test.carfleet.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    val instance: ApiService by lazy {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            readTimeout(90, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(90, TimeUnit.SECONDS)
        }.build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.carsApiBaseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}


