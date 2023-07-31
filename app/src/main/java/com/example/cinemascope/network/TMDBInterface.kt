package com.example.cinemascope.network

import com.example.cinemascope.data.Result
import com.example.cinemascope.data.MoviesResponse
import com.example.cinemascope.utils.Constants.API_KEY
import com.example.cinemascope.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface TMDBInterface {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") pageNumber: Int,
        @Query("region") region: String = "US",
    ): Response<MoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") pageNumber: Int,
        @Query("region") region: String = "US",
    ): Response<MoviesResponse>

    @GET("movie/now_playing")
    fun getNowShowingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") pageNumber: Int,
        @Query("region") region: String = "US",
    ): Response<MoviesResponse>

    @GET("/discover/movie")
    fun getDiscoverMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") pageNumber: Int,
        @Query("region") region: String = "US",
    ): Response<MoviesResponse>

    @GET("movie/{movieId}")
    fun getDetailMovie(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("append_to_response") response: String
    ): Response<Result>

    @GET("movie/{id}/videos")
    fun getMovieVideos(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String = API_KEY
    )
            : Response<Result>

    @GET("movie/{id}/credits")
    fun getMovieCredits(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Result>

    companion object {
        operator fun invoke(
        ): TMDBInterface {

            val url = BASE_URL
            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TMDBInterface::class.java)
        }
    }
}

