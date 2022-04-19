package com.davevarga.tmdbhub.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davevarga.tmdbhub.models.GenreString
import com.davevarga.tmdbhub.models.Movie

@Database(entities = arrayOf(Movie::class, GenreString::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

}