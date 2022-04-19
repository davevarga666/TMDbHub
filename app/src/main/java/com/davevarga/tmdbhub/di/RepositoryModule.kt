package com.davevarga.tmdbhub.di

import com.davevarga.tmdbhub.db.MovieDao
import com.davevarga.tmdbhub.network.GetData
import com.davevarga.tmdbhub.repository.MovieRepository
import com.davevarga.tmdbhub.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(movieDao: MovieDao): MovieRepository {
        return MovieRepository(movieDao)
    }

    @Singleton
    @Provides
    fun provideNetworkRepository(apiService: GetData): NetworkRepository {
        return NetworkRepository(apiService)
    }
}