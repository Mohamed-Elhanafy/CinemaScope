package com.example.cinemascope.ui.fragments.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemascope.data.MoviesResponse
import com.example.cinemascope.databinding.FragmentHomeBinding
import com.example.cinemascope.network.TMDBInterface
import com.example.cinemascope.ui.adapters.PopularMoviesAdapter
import com.example.cinemascope.utils.Constants.API_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val popularMoviesAdapter by lazy { PopularMoviesAdapter() }

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
        setupRecyclerView()

        //getPopularMovies and bind it with the adapter
        observePopularMovies()

    }

    private fun observePopularMovies() {
        viewModel.popularMovies.observe(viewLifecycleOwner) {
            popularMoviesAdapter.differ.submitList(it?.results)
        }
    }

    private fun setupRecyclerView() {
        binding.popularRecyclerView.apply {
            adapter = popularMoviesAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }


}