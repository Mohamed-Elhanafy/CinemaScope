package com.example.cinemascope.repository.local

import com.example.cinemascope.network.TMDBInterface

class MovieRepository(
    private val service: TMDBInterface
) {
    //popular movies
    suspend fun fetchPopularMovies(pageNumber: Int) =
        service.getPopularMovies(pageNumber = pageNumber)


}