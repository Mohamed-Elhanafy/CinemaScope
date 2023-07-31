package com.example.cinemascope.ui.fragments.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemascope.data.MoviesResponse
import com.example.cinemascope.network.TMDBInterface
import com.example.cinemascope.repository.local.MovieRepository
import com.example.cinemascope.utils.NetworkResult
import kotlinx.coroutines.launch


private const val TAG = "HomeViewModel"
class HomeViewModel : ViewModel() {

    private val api =  TMDBInterface.invoke()
    private val movieRepository = MovieRepository(api)
fun getPopularMovies() {
    viewModelScope.launch {
        val response = movieRepository.fetchPopularMovies(pageNumber = 1).body()

        val result = NetworkResult.Success(response as MoviesResponse)

        Log.i(TAG, "getPopularMovies: ${result.data}")

    }
}

}