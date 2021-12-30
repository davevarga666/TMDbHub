package com.davevarga.tmdbhub.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.davevarga.tmdbhub.models.Genre
import com.davevarga.tmdbhub.models.GenreList
import com.davevarga.tmdbhub.models.Movie
import com.davevarga.tmdbhub.network.*
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response

class NetworkRepository(private val apiService : GetData){

    var readyList: List<Genre>? = null

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var upcomingPagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory
    lateinit var upcomingDataSourceFactory: UpcomingDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable, minYear: String, maxYear: String, genre: String) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable, minYear, maxYear, genre)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getUpcoming(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        upcomingDataSourceFactory = UpcomingDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        upcomingPagedList = LivePagedListBuilder(upcomingDataSourceFactory, config).build()

        return upcomingPagedList
    }

    fun refreshMovies() {
        moviesDataSourceFactory.refresh()
    }

    suspend fun getAllGenres(): List<Genre>? {
        val genreList: Response<GenreList>

        genreList = ServiceBuilder.getNetworkClient(GetData::class.java)
            .getAllGenres(
                API_KEY
            )

        genreList.body()?.let {
            readyList = it.genres
        }
        return readyList
    }

}