
package com.example.cinemascope.ui.fragments.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cinemascope.databinding.FragmentMovieDetailsBinding
import com.example.cinemascope.ui.adapters.ListMoviesAdapter
import com.example.cinemascope.utils.Constants.BASE_URL_IMAGE

private const val TAG  = "MovieDetailsFragment"
class MovieDetailsFragment : Fragment() {


    private lateinit var binding: FragmentMovieDetailsBinding
    private val castingAdapter by lazy { ListMoviesAdapter() }
    private val args by navArgs<MovieDetailsFragmentArgs>()
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

        Log.i(TAG, "onViewCreated: $movie")

        Glide.with(requireActivity())
            .load(BASE_URL_IMAGE+movie.backdropPath)
            .into(binding.movieImage)

        setupCastingRecyclerView()

        binding.apply {
            titleText.text = movie.title
            ratingBar.rating = movie.voteAverage.toFloat()
            overviewText.text = movie.overview
            episodeText.text = movie.releaseDate
            seasonText.text = movie.runtime.toString()
            airDateText.text = movie.originalLanguage

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
