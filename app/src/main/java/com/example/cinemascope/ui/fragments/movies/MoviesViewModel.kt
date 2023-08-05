package com.example.cinemascope.ui.fragments.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemascope.data.MoviesResponse
import com.example.cinemascope.network.TMDBInterface
import com.example.cinemascope.repository.remote.MovieRepository
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {

    private val api = TMDBInterface.invoke()
    private val movieRepository = MovieRepository(api)

    private val _Movies = MutableLiveData<MoviesResponse?>()
    val Movies: LiveData<MoviesResponse?> = _Movies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun getAllMovies(){
        viewModelScope.launch {
            _isLoading.value = true
            val response = movieRepository.fetchPopularMovies(pageNumber = 1).body()
            _Movies.value = response
            if (response != null) {
                _isLoading.value = false
            }
        }
    }


}