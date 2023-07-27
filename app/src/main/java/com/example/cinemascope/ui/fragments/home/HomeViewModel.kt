package com.example.cinemascope.ui.fragments.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemascope.data.MoviesResponse
import com.example.cinemascope.network.TMDBInterface
import com.example.cinemascope.utils.Constants
import com.example.cinemascope.utils.Constants.API_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "HomeViewModel"
class HomeViewModel : ViewModel() {

    private val api =  TMDBInterface.invoke()
fun getPopularMovies() {
    viewModelScope.launch {
        val response = api.getPopularMovies(apiKey = API_KEY, pageNumber = 1).body()
        Log.i(TAG, "getPopularMovies: $response")

    }
}

}