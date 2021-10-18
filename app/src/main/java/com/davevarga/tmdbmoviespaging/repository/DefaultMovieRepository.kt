package com.davevarga.tmdbmoviespaging.repository

import com.davevarga.tmdbmoviespaging.db.MovieDao
import javax.inject.Inject

class DefaultMovieRepository @Inject constructor(private val movieDao: MovieDao) {
}