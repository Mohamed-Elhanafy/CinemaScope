package com.example.cinemascope.ui.fragments.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cinemascope.R
import com.example.cinemascope.data.Genre
import com.example.cinemascope.data.Movie
import com.example.cinemascope.data.Result
import com.example.cinemascope.databinding.FragmentHomeBinding
import com.example.cinemascope.ui.adapters.ListMoviesAdapter
import com.example.cinemascope.utils.Constants
import com.example.cinemascope.utils.Constants.BASE_URL_IMAGE
import kotlin.math.log

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    private val popularMoviesAdapter by lazy { ListMoviesAdapter() }
    private val upcomingMoviesAdapter by lazy { ListMoviesAdapter() }
    private val inTheatersMoviesAdapter by lazy { ListMoviesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding
            .inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPopularMovies()
        viewModel.getUpcomingMovies()
        viewModel.getInTheatersMovies()
        viewModel.getHighlightedMovie()



        observePopularMovies()
        observeUpcomingMovies()
        observeInTheatersMovies()
        observeHighlightedMovie()


        setupPopularMoviesRecyclerView()
        setupUpcomingMoviesRecyclerView()
        setupInTheatersMoviesRecyclerView()


    }


    private fun observeHighlightedMovie() {
        viewModel.highlightedMovie.observe(viewLifecycleOwner) {
            Log.i("TAG", "observeHighlightedMovie: $it")

            Glide.with(requireContext())
                .load(BASE_URL_IMAGE + it?.backdropPath)
                .into(binding.hlMovieImage)

            binding.hlMovieTitle.text = it?.title
            binding.hlNumOfVotes.text = it?.voteCount.toString()
            binding.hlRatingBar.rating = it?.voteAverage?.toFloat() ?: 0f

            binding.hlMovieImage.setOnClickListener { _ ->
                Log.i("TAG", "observeHighlightedMovie: ${it?.id}")
                navigateToMovieDetailsFragment(it)
            }
        }
    }

    private fun navigateToMovieDetailsFragment(it: Movie?) {
        val bundle = Bundle().apply {
            putParcelable(Constants.MOVIE_BUNDLE, it)
        }
        findNavController().navigate(
            R.id.action_homeFragment_to_movieDetailsFragment,
            bundle
        )
    }


    private fun observeInTheatersMovies() {
        viewModel.inTheatersMovies.observe(viewLifecycleOwner) {
            inTheatersMoviesAdapter.differ.submitList(it?.results)
        }
    }


    private fun observePopularMovies() {
        viewModel.popularMovies.observe(viewLifecycleOwner) {
            popularMoviesAdapter.differ.submitList(it?.results)
        }
    }

    private fun observeUpcomingMovies() {
        viewModel.upcomingMovies.observe(viewLifecycleOwner) {
            upcomingMoviesAdapter.differ.submitList(it?.results)
        }
    }

    private fun setupPopularMoviesRecyclerView() {
        binding.popularRecyclerView.apply {
            adapter = popularMoviesAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
        popularMoviesAdapter.onClick = {
            Log.i("TAG", "setupPopularMoviesRecyclerView: $it")
            navigateToMovieDetailsFragment(it)
        }
    }

    private fun setupUpcomingMoviesRecyclerView() {
        binding.upcomingRecyclerView.apply {
            adapter = upcomingMoviesAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
        upcomingMoviesAdapter.onClick = {
            Log.i("TAG", "setupPopularMoviesRecyclerView: $it")
            navigateToMovieDetailsFragment(it)

        }
    }

    private fun setupInTheatersMoviesRecyclerView() {
        binding.inTheatersRecyclerView.apply {
            adapter = inTheatersMoviesAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
        inTheatersMoviesAdapter.onClick = {
            Log.i("TAG", "setupPopularMoviesRecyclerView: $it")
            navigateToMovieDetailsFragment(it)

        }
    }

}