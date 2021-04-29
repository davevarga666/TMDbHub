package com.davevarga.tmdbmoviespaging.network

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.davevarga.tmdbmoviespaging.models.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiService : GetData, private val compositeDisposable: CompositeDisposable, val minYear: String, val maxYear: String, val genre: String)
    : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService,compositeDisposable, minYear, maxYear, genre)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }

    fun refresh() {
        moviesLiveDataSource.value?.invalidate()
    }

}