package com.example.cinemascope.ui.fragments.movies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinemascope.databinding.FragmentMoviesBinding
import com.example.cinemascope.ui.adapters.ListMoviesAdapter

private const val TAG = "MoviesFragment"

class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val viewModel by  viewModels<MoviesViewModel>()
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

        setupAllMoviesRecyclerView()

        observeMovies()
        observeLoading()

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
            //todo navigate to details fragment
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