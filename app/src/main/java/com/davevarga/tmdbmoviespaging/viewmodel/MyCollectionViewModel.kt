package com.davevarga.tmdbmoviespaging.ui

import android.app.Application
import androidx.lifecycle.*
import com.davevarga.tmdbmoviespaging.db.AppDatabase
import com.davevarga.tmdbmoviespaging.models.Movie
import com.davevarga.tmdbmoviespaging.repository.MovieRepository
import kotlinx.coroutines.launch

class MyCollectionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MovieRepository = MovieRepository(AppDatabase.getInstance(application).movieDao())

    var myMovieList: LiveData<List<Movie>>

    init {
        myMovieList = repository.getCollection()
    }

    fun insert(movie: Movie) {
        viewModelScope.launch {
            repository.insertMovie(movie)
        }

    }
}

@Suppress("UNCHECKED_CAST")
class MyCollectionViewModelFactory(
    val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyCollectionViewModel::class.java)) {
            return MyCollectionViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}