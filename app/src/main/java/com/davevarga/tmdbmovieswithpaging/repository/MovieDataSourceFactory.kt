package com.davevarga.tmdbmovieswithpaging.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.davevarga.tmdbmovieswithpaging.models.Movie
import com.davevarga.tmdbmovieswithpaging.network.GetData
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiService : GetData, private val compositeDisposable: CompositeDisposable, val minYear: String, val maxYear: String)
    : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService,compositeDisposable, minYear, maxYear)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }

    fun refresh() {
        moviesLiveDataSource.value?.invalidate()
    }

}