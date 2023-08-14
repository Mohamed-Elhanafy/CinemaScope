package com.example.cinemascope.ui.fragments.tv_shows

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinemascope.R
import com.example.cinemascope.databinding.FragmentTvShowsBinding
import com.example.cinemascope.ui.adapters.ListMoviesAdapter
import com.example.cinemascope.utils.Constants


private const val TAG = "TvShowsFragment"
class TvShowsFragment : Fragment() {

    private val viewModel by viewModels<TvShowsViewModel>()
    private lateinit var binding: FragmentTvShowsBinding

    //todo you need to chance this adapter with tvshows adapter

    private val allShowsAdapter by lazy { ListMoviesAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTvShowsBinding
            .inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllShows()

        Log.i(TAG, "onViewCreated")

        observeShows()

        observeLoading()

        setupAllMoviesRecyclerView()


    }

    private fun setupAllMoviesRecyclerView() {
        binding.showAllShowsRecyclerView.apply {
            adapter = allShowsAdapter
            layoutManager = GridLayoutManager(
                requireActivity(), 3
            )
        }

        allShowsAdapter.onClick = {
            Log.i(TAG, "onViewCreated: $it")
            val bundle = Bundle().apply {
                putParcelable(Constants.MOVIE_BUNDLE, it)
            }
            findNavController().navigate(
                R.id.action_moviesFragment_to_movieDetailsFragment,
                bundle
            )

        }
    }

    private fun observeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.showsProgressBar.visibility = View.VISIBLE
            } else {
                binding.showsProgressBar.visibility = View.GONE
            }
        }
    }

    private fun observeShows() {
        viewModel.shows.observe(viewLifecycleOwner) {
            allShowsAdapter.differ.submitList(it?.results)
            Log.i(TAG, "observeShows: $it  ")
        }
    }
}