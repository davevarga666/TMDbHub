package com.davevarga.tmdbmovieswithpaging.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import com.davevarga.tmdbmovieswithpaging.db.MovieDao
import com.davevarga.tmdbmovieswithpaging.models.Movie
import com.davevarga.tmdbmovieswithpaging.network.GetData
import com.davevarga.tmdbmovieswithpaging.network.ServiceBuilder
import com.davevarga.tmdbmovieswithpaging.ui.MovieRecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val movieDao: MovieDao) {

    fun listenForMovies(): DataSource.Factory<Int, Movie> = movieDao.listenForMovies()

    suspend fun insertMovieList(minYear: String, maxYear: String) {
        withContext(Dispatchers.IO) {
            val movieList = ServiceBuilder.getNetworkClient(GetData::class.java)
                .getDataByReleaseWindow(
                    "d00127676d268780e41811f616e4fbb0",
                    minYear + "-01-01",
                    maxYear + "-12-31"
                )
            movieDao.insertMovieList(MovieRecyclerAdapter(movieList.body()!!.movieList, null).items)
        }
    }


}