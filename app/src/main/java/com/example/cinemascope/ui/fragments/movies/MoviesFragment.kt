package com.example.cinemascope.ui.fragments.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinemascope.R
import com.example.cinemascope.databinding.FragmentMoviesBinding
import com.example.cinemascope.ui.adapters.ListMoviesAdapter
import com.example.cinemascope.utils.Constants.MOVIE_BUNDLE


private const val TAG = "MoviesFragment"

class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val viewModel by viewModels<MoviesViewModel>()
    private val allMoviesAdapter by lazy { ListMoviesAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllMovies()

        observeMovies()
        observeLoading()

        setupAllMoviesRecyclerView()


    }

    private fun setupAllMoviesRecyclerView() {
        binding.showAllRecyclerView.apply {
            adapter = allMoviesAdapter
            layoutManager = GridLayoutManager(
                requireActivity(), 3
            )
        }

        allMoviesAdapter.onClick = {
            Log.i(TAG, "onViewCreated: $it")
            val bundle = Bundle().apply {
                putParcelable(MOVIE_BUNDLE, it)
            }
            findNavController().navigate(
                R.id.action_moviesFragment_to_movieDetailsFragment,
                bundle
            )

        }
    }


    private fun observeMovies() {
        viewModel.Movies.observe(viewLifecycleOwner) {
            allMoviesAdapter.differ.submitList(it?.results)
        }
    }

    private fun observeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.moviesProgressBar.visibility = View.VISIBLE
            } else {
                binding.moviesProgressBar.visibility = View.GONE
            }
        }
    }


}