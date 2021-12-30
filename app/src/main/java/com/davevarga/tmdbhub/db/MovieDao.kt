package com.davevarga.tmdbhub.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.davevarga.tmdbhub.models.GenreString
import com.davevarga.tmdbhub.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun getCollection(): LiveData<MutableList<Movie>>

    @Query("SELECT * FROM genre_table")
    fun getGenres(): LiveData<List<GenreString>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: GenreString)

    @Query("DELETE FROM movie_table")
    suspend fun deleteMovies()

    @Query("DELETE FROM movie_table WHERE id = :id ")
    suspend fun deleteMovie(id: Int)

    @Query("DELETE FROM genre_table")
    suspend fun deleteGenres()

}