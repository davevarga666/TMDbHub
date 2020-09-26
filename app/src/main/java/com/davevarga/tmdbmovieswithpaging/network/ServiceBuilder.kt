package com.davevarga.tmdbmovieswithpaging.network

import androidx.databinding.library.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    fun getNetworkClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else
            interceptor.level = HttpLoggingInterceptor.Level.NONE


        return OkHttpClient.Builder().addNetworkInterceptor(interceptor).build()


    }


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(getNetworkClient())
        .build()

    fun <T> getNetworkClient(service: Class<T>): T {
        return retrofit.create(service)
    }
}