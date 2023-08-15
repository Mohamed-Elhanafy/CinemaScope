package com.example.cinemascope.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemascope.data.Movie
import com.example.cinemascope.data.Show
import com.example.cinemascope.databinding.ListItemMovieBinding
import com.example.cinemascope.databinding.ListItemTvShowBinding
import com.example.cinemascope.utils.Constants.BASE_URL_IMAGE

class ListShowsAdapter : RecyclerView.Adapter<ListShowsAdapter.ListShowsViewHolder>() {
    inner class ListShowsViewHolder(private val binding: ListItemTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Show) {
            binding.apply {
                Glide.with(itemView)
                    .load(BASE_URL_IMAGE + show.posterPath)
                    .into(image)
                titleText.text = show.name
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Show>() {
        override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem.id == newItem.id
        }

    }
    val differ = AsyncListDiffer(this, diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListShowsViewHolder {
        return ListShowsViewHolder(
            ListItemTvShowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ListShowsViewHolder, position: Int) {
        val show = differ.currentList[position]
        holder.bind(show)

        holder.itemView.setOnClickListener {
            onClick?.let {
                it(show)
            }
        }

    }

    var onClick: ((Show) -> Unit)? = null
}