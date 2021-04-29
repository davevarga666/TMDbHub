package com.davevarga.tmdbmoviespaging.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.davevarga.tmdbmoviespaging.models.Genre
import com.davevarga.tmdbmoviespaging.models.GenreString
import com.davevarga.tmdbmoviespaging.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun getCollection(): LiveData<List<Movie>>

    @Query("SELECT * FROM genre_table")
    fun getGenres(): LiveData<List<GenreString>>

    @Query("SELECT * FROM genre_table")
    suspend fun getGenresNLD(): List<GenreString>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: GenreString)

    @Query("DELETE FROM movie_table")
    suspend fun deleteMovies()

    @Query("DELETE FROM genre_table")
    suspend fun deleteGenres()

}