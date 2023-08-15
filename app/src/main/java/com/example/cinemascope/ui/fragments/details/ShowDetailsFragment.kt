package com.example.cinemascope.ui.fragments.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cinemascope.databinding.FragmentShowDetailsBinding
import com.example.cinemascope.network.TMDBInterface
import com.example.cinemascope.repository.remote.MovieRepository
import com.example.cinemascope.ui.adapters.ListCastAdapter
import com.example.cinemascope.ui.adapters.ListVideoAdapter
import com.example.cinemascope.utils.Constants
import kotlinx.coroutines.launch


private const val TAG = "ShowDetailsFragment"

class ShowDetailsFragment: Fragment() {
    private lateinit var binding: FragmentShowDetailsBinding
    private val castingAdapter by lazy { ListCastAdapter() }
    private val videoAdapter by lazy { ListVideoAdapter() }
    private val showArgs by navArgs<ShowDetailsFragmentArgs>()

    private val api = TMDBInterface.invoke()
    private val movieRepository = MovieRepository(api)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowDetailsBinding
            .inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val movie = showArgs.showBundle

        getVideos(movie.id.toString())
        getCast(movie.id.toString())

        Log.i(TAG, "onViewCreated: $movie")

        Glide.with(requireActivity())
            .load(Constants.BASE_URL_IMAGE + movie.backdropPath)
            .into(binding.movieImage)

        setupCastingRecyclerView()
        setupVideoRecyclerView()



        binding.apply {
            titleText.text = movie.name
            ratingBar.rating = movie.voteAverage.toFloat()
            overviewText.text = movie.overview
            episodeText.text = movie.firstAirDate
            //seasonText.text = movie.runtime.toString()
            airDateText.text = movie.originalLanguage
        }
    }


    private fun getCast(id: String) {
        lifecycleScope.launch {
            val response = movieRepository.fetchCast(id.toLong()).body()

            Log.i(TAG, "getCasts: $response")

            val differ = response?.cast?.let { castingAdapter.differ.submitList(it) }
        }
    }

    private fun setupVideoRecyclerView() {
        binding.videosRecyclerView.apply {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        videoAdapter.onClick = {
            Log.i(TAG, "setupVideoRecyclerView: $it")
        }
    }

    private fun getVideos(id: String) {
        lifecycleScope.launch {
            val response = movieRepository.fetchVideos(id.toLong()).body()

            Log.i(TAG, "getVideos: $response")

            val differ = response?.results?.let { videoAdapter.differ.submitList(it) }
        }
    }

    private fun setupCastingRecyclerView() {
        binding.castRecyclerView.apply {
            adapter = castingAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        }
        castingAdapter.onClick = {
            Log.i("TAG", " $it")
        }
    }
}