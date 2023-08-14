package com.example.cinemascope.ui.fragments.tv_shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemascope.data.MoviesResponse
import com.example.cinemascope.network.TMDBInterface
import com.example.cinemascope.repository.remote.MovieRepository
import kotlinx.coroutines.launch

class TvShowsViewModel : ViewModel() {

    private val api = TMDBInterface.invoke()
    private val movieRepository = MovieRepository(api)

    private val _shows = MutableLiveData<MoviesResponse?>()
    val shows: LiveData<MoviesResponse?> = _shows

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun getAllShows(){
        viewModelScope.launch {
            _isLoading.value = true
            val response = movieRepository.fetchPopularShows(pageNumber = 1).body()
            _shows.value = response
            if (response != null) {
                _isLoading.value = false
            }
        }
    }

}