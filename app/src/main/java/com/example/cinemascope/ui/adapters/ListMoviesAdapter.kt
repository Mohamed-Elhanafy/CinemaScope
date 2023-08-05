package com.example.cinemascope.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemascope.data.Movie
import com.example.cinemascope.databinding.ListItemMovieBinding
import com.example.cinemascope.utils.Constants.BASE_URL_IMAGE

class ListMoviesAdapter : RecyclerView.Adapter<ListMoviesAdapter.ListMoviesViewHolder>() {
    inner class ListMoviesViewHolder(private val binding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                Glide.with(itemView)
                    .load(BASE_URL_IMAGE + movie.posterPath)
                    .into(image)
                titleText.text = movie.title
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

    }
    val differ = AsyncListDiffer(this, diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMoviesViewHolder {
        return ListMoviesViewHolder(
            ListItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ListMoviesViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)

        holder.itemView.setOnClickListener {
            onClick?.let {
                it(movie)
            }
        }

    }

    var onClick: ((Movie) -> Unit)? = null
}