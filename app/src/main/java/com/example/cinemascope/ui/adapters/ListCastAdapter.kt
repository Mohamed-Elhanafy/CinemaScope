package com.example.cinemascope.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemascope.data.CastX
import com.example.cinemascope.data.Cast
import com.example.cinemascope.databinding.ListItemMovieBinding
import com.example.cinemascope.utils.Constants.BASE_URL_IMAGE

class ListCastAdapter : RecyclerView.Adapter<ListCastAdapter.ListCastViewHolder>() {
    inner class ListCastViewHolder(private val binding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: CastX) {
            binding.apply {
                Glide.with(itemView)
                    .load(BASE_URL_IMAGE + cast.profilePath)
                    .into(image)
                titleText.text = cast.name
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CastX>() {
        override fun areItemsTheSame(oldItem: CastX, newItem: CastX): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CastX, newItem: CastX): Boolean {
            return oldItem.id == newItem.id
        }

    }
    val differ = AsyncListDiffer(this, diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCastViewHolder {
        return ListCastViewHolder(
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

    override fun onBindViewHolder(holder: ListCastViewHolder, position: Int) {
        val cast = differ.currentList[position]
        holder.bind(cast)

        holder.itemView.setOnClickListener {
            onClick?.let {
                it(cast)
            }
        }

    }

    var onClick: ((CastX) -> Unit)? = null
}