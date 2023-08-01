package com.example.cinemascope.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemascope.data.Result
import com.example.cinemascope.databinding.ListItemMovieBinding
import com.example.cinemascope.utils.Constants.BASE_URL_IMAGE

class InTheatersMoviesAdapter : RecyclerView.Adapter<InTheatersMoviesAdapter.InTheatersMoviesViewHolder>() {
    inner class InTheatersMoviesViewHolder(private val binding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.apply {
                Glide.with(itemView)
                    .load(BASE_URL_IMAGE + result.posterPath)
                    .into(image)
                titleText.text = result.title
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

    }
    val differ = AsyncListDiffer(this, diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InTheatersMoviesViewHolder {
        return InTheatersMoviesViewHolder(
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

    override fun onBindViewHolder(holder: InTheatersMoviesViewHolder, position: Int) {
        val result = differ.currentList[position]
        holder.bind(result)
    }
}