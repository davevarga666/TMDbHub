package com.davevarga.tmdbhub.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.davevarga.tmdbhub.models.GenreString
import com.davevarga.tmdbhub.repository.MovieRepository
import com.davevarga.tmdbhub.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    application: Application,
    private val networkRepository: NetworkRepository,
    private val movieRepository: MovieRepository
) : AndroidViewModel(application) {

    var genreList = liveData(Dispatchers.IO) {
        emit(networkRepository.getAllGenres())
    }

    fun insert(genres: GenreString) {
        viewModelScope.launch {
            movieRepository.insertGenres(genres)
        }
    }
}