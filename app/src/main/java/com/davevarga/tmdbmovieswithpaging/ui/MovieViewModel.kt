package com.davevarga.tmdbmovieswithpaging.ui

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.*
import com.davevarga.tmdbmovieswithpaging.db.AppDatabase
import com.davevarga.tmdbmovieswithpaging.models.Movie
import com.davevarga.tmdbmovieswithpaging.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(application: Application, minYear: String, maxYear: String) : AndroidViewModel(application) {

    private val repository: MovieRepository =
        MovieRepository(AppDatabase.getInstance(application).movieDao())

    private val minimumYear = minYear
    private val maximumYear = maxYear


    private val liveResults: LiveData<PagedList<Movie>>


    init {
        insert(minimumYear, maximumYear)

        liveResults = LivePagedListBuilder(
            repository.listenForMovies(),
            PagedList.Config.Builder()
                .setPageSize(20)
                .setPrefetchDistance(20)
                .setEnablePlaceholders(true)
                .build())
            .setInitialLoadKey(0)
            .build()

    }

    fun getMoviesPaged() = liveResults

    fun insert(minYear: String, maxYear: String) {
        viewModelScope.launch {
            repository.insertMovieList(minYear,  maxYear)
        }

    }

}

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(val application: Application, val minYear: String, val maxYear: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(application, minYear, maxYear) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}