package com.example.cinemascope.repository.remote

import com.example.cinemascope.network.TMDBInterface

class MovieRepository(
    private val service: TMDBInterface
) {

    suspend fun fetchPopularMovies(pageNumber: Int) =
        service.getPopularMovies(pageNumber = pageNumber)

    suspend fun fetchPopularShows(pageNumber: Int) =
        service.getPopularShows(pageNumber = pageNumber)

    suspend fun fetchUpcomingMovies(pageNumber: Int) =
        service.getUpcomingMovies(pageNumber = pageNumber)

    suspend fun fetchInTheatersMovies(pageNumber: Int) =
        service.getNowShowingMovies(pageNumber = pageNumber)

    suspend fun fetchGenres() =
        service.getMovieGenreList()

    suspend fun fetchVideos(movieId: Long) =
        service.getMovieVideos(id = movieId)

    suspend fun fetchCast(movieId: Long) =
        service.getMovieCredits(id = movieId)
}
