package com.davevarga.tmdbmovieswithpaging.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.davevarga.tmdbmovieswithpaging.models.Movie
import com.davevarga.tmdbmovieswithpaging.repository.MovieRepository
import com.davevarga.tmdbmovieswithpaging.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(private val movieRepository: MovieRepository, val minYear: String, val maxYear: String) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList: LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable, minYear, maxYear)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
    @Suppress("UNCHECKED_CAST")
    class MovieViewModelFactory(
        val movieRepository: MovieRepository,
        val minYear: String,
        val maxYear: String
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
                return MovieViewModel(movieRepository, minYear, maxYear) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }