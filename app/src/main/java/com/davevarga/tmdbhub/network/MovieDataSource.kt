package com.davevarga.tmdbhub.network

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.davevarga.tmdbhub.models.Movie
import com.davevarga.tmdbhub.repository.NetworkState
import com.davevarga.tmdbhub.util.API_KEY
import com.davevarga.tmdbhub.util.END_OF_YEAR
import com.davevarga.tmdbhub.util.FIRST_PAGE
import com.davevarga.tmdbhub.util.START_OF_YEAR
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(
    private val apiService: GetData,
    private val compositeDisposable: CompositeDisposable,
    val minYear: String,
    val maxYear: String,
    val genre: String
) : PageKeyedDataSource<Int, Movie>() {


    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getFilteredMovies(
                API_KEY,
                minYear + START_OF_YEAR,
                maxYear + END_OF_YEAR,
                genre,
                page
            )
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movieList, null, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getFilteredMovies(
                API_KEY,
                minYear + START_OF_YEAR,
                maxYear + END_OF_YEAR,
                genre,
                params.key
            )
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.totalPages >= params.key) {
                            callback.onResult(it.movieList, params.key + 1)
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
}