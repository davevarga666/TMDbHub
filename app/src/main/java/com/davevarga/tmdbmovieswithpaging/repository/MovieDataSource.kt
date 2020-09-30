package com.davevarga.tmdbmovieswithpaging.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.davevarga.tmdbmovieswithpaging.models.Movie
import com.davevarga.tmdbmovieswithpaging.network.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource (private val apiService : GetData, private val compositeDisposable: CompositeDisposable, val minYear: String, val maxYear: String)
    : PageKeyedDataSource<Int, Movie>(){

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getDataByReleaseWindow(API_KEY, minYear + START_OF_YEAR, maxYear + END_OF_YEAR, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movieList, null, page+1)
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
            apiService.getDataByReleaseWindow(API_KEY, minYear + START_OF_YEAR, maxYear + END_OF_YEAR, params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.totalPages >= params.key) {
                            callback.onResult(it.movieList, params.key+1)
                            networkState.postValue(NetworkState.LOADED)
                        }
                        else{
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