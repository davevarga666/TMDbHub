package com.davevarga.tmdbmoviespaging.network

import androidx.databinding.library.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val API_KEY = "6e63c2317fbe963d76c3bdc2b785f6d1"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val FIRST_PAGE = 1
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"
const val POST_PER_PAGE = 20
const val START_OF_YEAR = "-01-01"
const val END_OF_YEAR = "-12-31"
const val FULL_URL = "https://api.themoviedb.org/3/movie/550?api_key=d00127676d268780e41811f616e4fbb0&sort_by=popularity.desc"

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
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(getNetworkClient())
        .build()

    fun <T> getNetworkClient(service: Class<T>): T {
        return retrofit.create(service)
    }
}