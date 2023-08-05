package com.example.cinemascope.repository.remote

import com.example.cinemascope.network.TMDBInterface

class MovieRepository(
    private val service: TMDBInterface
) {

    suspend fun fetchPopularMovies(pageNumber: Int) =
        service.getPopularMovies(pageNumber = pageNumber)

    suspend fun fetchUpcomingMovies(pageNumber: Int) =
        service.getUpcomingMovies(pageNumber = pageNumber)

    suspend fun fetchInTheatersMovies(pageNumber: Int) =
        service.getNowShowingMovies(pageNumber = pageNumber)

    suspend fun fetchGenres() =
        service.getMovieGenreList()
}