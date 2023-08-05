package com.example.cinemascope.ui.fragments.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemascope.data.Genre
import com.example.cinemascope.data.MoviesResponse
import com.example.cinemascope.data.Movie
import com.example.cinemascope.network.TMDBInterface
import com.example.cinemascope.repository.remote.MovieRepository
import kotlinx.coroutines.launch


private const val TAG = "HomeViewModel"

class HomeViewModel : ViewModel() {

    private val api = TMDBInterface.invoke()
    private val movieRepository = MovieRepository(api)

    private val _popularMovies = MutableLiveData<MoviesResponse?>()
    val popularMovies: LiveData<MoviesResponse?> = _popularMovies

    private val _upcomingMovies = MutableLiveData<MoviesResponse?>()
    val upcomingMovies: LiveData<MoviesResponse?> = _upcomingMovies

    private val _inTheatersMovies = MutableLiveData<MoviesResponse?>()
    val inTheatersMovies: LiveData<MoviesResponse?> = _inTheatersMovies

    private val _highlightedMovie = MutableLiveData<Movie?>()
    val highlightedMovie: LiveData<Movie?> = _highlightedMovie

    private val _genreName = MutableLiveData<List<Genre>?>()
    val genreName: LiveData<List<Genre>?> = _genreName
    fun getPopularMovies() {
        viewModelScope.launch {
            val response = movieRepository.fetchPopularMovies(pageNumber = 1).body()
            Log.i(TAG, "getPopularMovies: $response")
            _popularMovies.value = response
        }
    }

    fun getUpcomingMovies() {
        viewModelScope.launch {
            val upcomingMovies = movieRepository.fetchUpcomingMovies(pageNumber = 1).body()
            Log.i(TAG, "getUpcomingMovies: $upcomingMovies")
            _upcomingMovies.value = upcomingMovies
        }
    }

    fun getInTheatersMovies() {
        viewModelScope.launch {
            val inTheatersMovies = movieRepository.fetchInTheatersMovies(pageNumber = 1).body()
            Log.i(TAG, "getInTheatersMovies: $inTheatersMovies")
            _inTheatersMovies.value = inTheatersMovies
        }
    }

    fun getHighlightedMovie() {
        viewModelScope.launch {
            val highlightedMovie =
                movieRepository.fetchPopularMovies(pageNumber = 1).body()?.results?.get(0)
            Log.i(TAG, "getHighlightedMovie: $highlightedMovie")
            _highlightedMovie.value = highlightedMovie
        }
    }

    fun getGenreName() {
        viewModelScope.launch {
            val genres = movieRepository.fetchGenres().body()
            _genreName.value = genres?.genres
        }
    }
}