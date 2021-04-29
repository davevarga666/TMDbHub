package com.davevarga.tmdbmoviespaging.network

import com.davevarga.tmdbmoviespaging.models.GenreList
import com.davevarga.tmdbmoviespaging.models.MovieResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetData {

    @GET("discover/movie")
    fun getFilteredMovies(
        @Query("api_key") apiKey: String,
        @Query("primary_release_date.gte") releaseDateGte: String,
        @Query("primary_release_date.lte") releaseDateLte: String,
        @Query("with_genres") genres: String,
        @Query("page") page: Int

    ): Single<MovieResponse>

    @GET("movie/upcoming")
    fun getUpcoming(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("genre/movie/list")
    suspend fun getAllGenres(
        @Query("api_key") apiKey: String
    ): Response<GenreList>

}

//api_key=d00127676d268780e41811f616e4fbb0&sort_by=popularity.desc&primary_release_year=2019