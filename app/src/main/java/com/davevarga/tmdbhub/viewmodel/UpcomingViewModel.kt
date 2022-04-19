package com.davevarga.tmdbhub.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.davevarga.tmdbhub.models.Movie
import com.davevarga.tmdbhub.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class UpcomingViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val upcomingPagedList: LiveData<PagedList<Movie>> by lazy {
        networkRepository.getUpcoming(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}