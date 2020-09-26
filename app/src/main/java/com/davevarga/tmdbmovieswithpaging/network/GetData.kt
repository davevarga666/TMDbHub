package com.davevarga.tmdbmovieswithpaging.network

import com.davevarga.tmdbmovieswithpaging.models.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetData {

    @GET("discover/movie")
    suspend fun getDataByReleaseWindow(
        @Query("api_key") apiKey: String,
        @Query("primary_release_date.gte") releaseDateGte: String,
        @Query("primary_release_date.lte") releaseDateLte: String

    ): Response<MovieList>

}

//api_key=d00127676d268780e41811f616e4fbb0&sort_by=popularity.desc&primary_release_year=2019