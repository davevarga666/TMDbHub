package com.davevarga.tmdbhub.di

import android.content.Context
import androidx.room.Room
import com.davevarga.tmdbhub.BaseApplication
import com.davevarga.tmdbhub.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, AppDatabase::class.java, "movieDB").build()

    @Singleton
    @Provides
    fun provideMovieDao(database: AppDatabase) =
        database.movieDao()
}

