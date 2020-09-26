package com.davevarga.tmdbmovieswithpaging.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.davevarga.tmdbmovieswithpaging.models.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table")
    fun listenForMovies(): DataSource.Factory<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movieList: List<Movie>)

}