package com.example.cinemascope.ui.fragments.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cinemascope.databinding.FragmentMovieDetailsBinding
import com.example.cinemascope.network.TMDBInterface
import com.example.cinemascope.repository.remote.MovieRepository
import com.example.cinemascope.ui.adapters.ListCastAdapter
import com.example.cinemascope.ui.adapters.ListMoviesAdapter
import com.example.cinemascope.ui.adapters.ListVideoAdapter
import com.example.cinemascope.utils.Constants.BASE_URL_IMAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MovieDetailsFragment"

class MovieDetailsFragment : Fragment() {


    private lateinit var binding: FragmentMovieDetailsBinding
    private val castingAdapter by lazy { ListCastAdapter() }
    private val videoAdapter by lazy { ListVideoAdapter() }
    private val args by navArgs<MovieDetailsFragmentArgs>()

    private val api = TMDBInterface.invoke()
    private val movieRepository = MovieRepository(api)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val movie = args.movieBundle
        getVideos(movie.id.toString())
        getCast(movie.id.toString())

        Log.i(TAG, "onViewCreated: $movie")

        Glide.with(requireActivity())
            .load(BASE_URL_IMAGE + movie.backdropPath)
            .into(binding.movieImage)

        setupCastingRecyclerView()
        setupVideoRecyclerView()



        binding.apply {
            titleText.text = movie.title
            ratingBar.rating = movie.voteAverage.toFloat()
            overviewText.text = movie.overview
            episodeText.text = movie.releaseDate
            seasonText.text = movie.runtime.toString()
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
