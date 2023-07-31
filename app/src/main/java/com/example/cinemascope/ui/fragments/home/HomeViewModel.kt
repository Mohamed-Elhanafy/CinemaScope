package com.example.cinemascope.ui.fragments.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemascope.data.MoviesResponse
import com.example.cinemascope.data.Result
import com.example.cinemascope.network.TMDBInterface
import com.example.cinemascope.repository.local.MovieRepository
import com.example.cinemascope.utils.NetworkResult
import kotlinx.coroutines.launch


private const val TAG = "HomeViewModel"
class HomeViewModel : ViewModel() {

    private val api =  TMDBInterface.invoke()
    private val movieRepository = MovieRepository(api)

    private val _popularMovies = MutableLiveData<MoviesResponse?>()
    val popularMovies: LiveData<MoviesResponse?> = _popularMovies

fun getPopularMovies() {
    viewModelScope.launch {
        val response = movieRepository.fetchPopularMovies(pageNumber = 1).body()
        Log.i(TAG, "getPopularMovies: $response")
        _popularMovies.value = response
    }
}

}