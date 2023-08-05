package com.example.cinemascope.ui.fragments.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.cinemascope.R
import com.example.cinemascope.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel by viewModels<MovieDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding
            .inflate(inflater, container, false)
        return binding.root
    }


}