package com.davevarga.tmdbhub.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.davevarga.tmdbhub.models.Movie
import com.davevarga.tmdbhub.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class MovieViewModel  @Inject constructor(
    application: Application,
    private val networkRepository: NetworkRepository
) :
    AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    lateinit var moviePagedList: LiveData<PagedList<Movie>>

    fun fetchPagedList(
        compositeDisposable: CompositeDisposable, minimumYear: String,
        maximumYear: String,
        mGenre: String
    ) {
        moviePagedList = networkRepository.fetchLiveMoviePagedList(
            compositeDisposable,
            minimumYear,
            maximumYear,
            mGenre
        )
    }

    fun refresh() {
        networkRepository.refreshMovies()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}