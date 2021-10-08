package com.davevarga.tmdbmoviespaging.repository

import androidx.lifecycle.LiveData
import com.davevarga.tmdbmoviespaging.db.MovieDao
import com.davevarga.tmdbmoviespaging.models.GenreString
import com.davevarga.tmdbmoviespaging.models.Movie

class MovieRepository(private val movieDao: MovieDao) {


    fun getCollection(): LiveData<List<Movie>> = movieDao.getCollection()

    suspend fun insertMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    suspend fun insertGenres(genres: GenreString) {
        movieDao.insertGenres(genres)
    }

    suspend fun deleteGenres() {
        movieDao.deleteGenres()
    }

}