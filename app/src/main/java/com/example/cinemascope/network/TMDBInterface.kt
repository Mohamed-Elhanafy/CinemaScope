package com.example.cinemascope.network

import androidx.lifecycle.MutableLiveData
import com.example.cinemascope.data.MoviesResponse
import com.example.cinemascope.utils.Constants.BASE_URL
import retrofit2.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface TMDBInterface {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") pageNumber: Int,
        @Query("region") region: String = "US",
    ): Response<MoviesResponse>


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

