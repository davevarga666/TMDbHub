package com.davevarga.tmdbhub.repository

import androidx.lifecycle.LiveData
import com.davevarga.tmdbhub.db.MovieDao
import com.davevarga.tmdbhub.models.GenreString
import com.davevarga.tmdbhub.models.Movie

class MovieRepository(private val movieDao: MovieDao) {

    fun getCollection(): LiveData<MutableList<Movie>> = movieDao.getCollection()

    suspend fun insertMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    suspend fun insertGenres(genres: GenreString) {
        movieDao.insertGenres(genres)
    }

    suspend fun deleteGenres() {
        movieDao.deleteGenres()
    }

    suspend fun deleteMovie(id: Int) {
        movieDao.deleteMovie(id)
    }

}