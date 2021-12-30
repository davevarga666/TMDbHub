package com.davevarga.tmdbhub.ui

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.davevarga.tmdbhub.models.Movie
import com.davevarga.tmdbhub.repository.NetworkRepository
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(
    application: Application,
    private val networkRepository: NetworkRepository,
    var minYear: String,
    var maxYear: String,
    var genres: String
) :
    AndroidViewModel(application) {

    private val minimumYear = minYear
    private val maximumYear = maxYear
    private val mGenre = genres
    private val compositeDisposable = CompositeDisposable()

    var moviePagedList: LiveData<PagedList<Movie>>

    init {
        moviePagedList = networkRepository.fetchLiveMoviePagedList(compositeDisposable, minimumYear, maximumYear, mGenre)
    }

    fun refresh() {
        networkRepository.refreshMovies()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(
    val application: Application,
    val networkRepository: NetworkRepository,
    val minYear: String,
    val maxYear: String,
    val genres: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(application, networkRepository, minYear, maxYear, genres) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}