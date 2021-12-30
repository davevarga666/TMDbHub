package com.davevarga.tmdbhub.ui

import android.app.Application
import androidx.lifecycle.*
import com.davevarga.tmdbhub.db.AppDatabase
import com.davevarga.tmdbhub.models.GenreString
import com.davevarga.tmdbhub.repository.MovieRepository
import com.davevarga.tmdbhub.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenreViewModel(application: Application, private val networkRepository: NetworkRepository) : AndroidViewModel(application) {

    private val movieRepository =  MovieRepository(AppDatabase.getInstance(application).movieDao())
    var genreList = liveData(Dispatchers.IO) {
        emit(networkRepository.getAllGenres())
    }

    fun insert(genres: GenreString) {
        viewModelScope.launch {
            movieRepository.insertGenres(genres)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class GenreViewModelFactory(
    val networkRepository: NetworkRepository,
    val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GenreViewModel::class.java)) {
            return GenreViewModel(application, networkRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}