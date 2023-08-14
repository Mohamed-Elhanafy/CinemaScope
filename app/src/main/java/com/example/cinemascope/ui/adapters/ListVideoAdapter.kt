package com.example.cinemascope.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.cinemascope.data.ResultX
import com.example.cinemascope.data.Video
import com.example.cinemascope.databinding.ListItemMovieBinding
import com.example.cinemascope.databinding.ListItemVideoBinding
import com.example.cinemascope.utils.getYoutubeThumbnailUrlFromVideoUrl

class ListVideoAdapter : RecyclerView.Adapter<ListVideoAdapter.ListVideoViewHolder>() {
    inner class ListVideoViewHolder (private val binding:ListItemVideoBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(video: ResultX) {
            binding.apply {
                Glide.with(itemView)
                    .load(getYoutubeThumbnailUrlFromVideoUrl(video.key))
                    .into(image)
                    .view.scaleType = android.widget.ImageView.ScaleType.CENTER_CROP
            }
        }
    }
    private val diffCallback = object : DiffUtil.ItemCallback<ResultX>() {
        override fun areItemsTheSame(oldItem: ResultX, newItem: ResultX): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultX, newItem: ResultX): Boolean {
            return oldItem.id == newItem.id
        }

    }
    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVideoViewHolder {
        return ListVideoViewHolder(
            ListItemVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ListVideoViewHolder, position: Int) {
        val video = differ.currentList[position]
        holder.bind(video)

        holder.itemView.setOnClickListener {
            onClick?.let {
                it(video)
            }
        }

    }
    var onClick: ((ResultX) -> Unit)? = null

}