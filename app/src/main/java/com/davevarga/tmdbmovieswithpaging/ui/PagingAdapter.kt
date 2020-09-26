package com.davevarga.tmdbmovieswithpaging.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.davevarga.tmdbmovieswithpaging.R
import com.davevarga.tmdbmovieswithpaging.databinding.LayoutMovieListItemBinding
import com.davevarga.tmdbmovieswithpaging.models.Movie

class PagingAdapter(var clickListener: MovieClickListener?) :
    PagedListAdapter<Movie, PagingAdapter.PagingViewHolder>(DIFF_CALLBACK) {

    lateinit var binding: LayoutMovieListItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PagingAdapter.PagingViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_movie_list_item,
            parent,
            false
        )

        return PagingViewHolder(
            binding.root
        )
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        return holder.bind(getItem(position), binding, clickListener!!)
    }

    //to remove duplicates
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Movie, newItem: Movie
            ) = oldItem == newItem
        }
    }


    class PagingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            movieItem: Movie?,
            binding: LayoutMovieListItemBinding,
            clickListener: MovieClickListener
        ) {
            binding.movieItem = movieItem

            itemView.setOnClickListener {
                clickListener.onItemClick(movieItem!!, adapterPosition)
            }

            val circularProgressDrawable = CircularProgressDrawable(itemView.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load("http://image.tmdb.org/t/p/w500/" + movieItem!!.posterPath)
                .error(R.drawable.error_image)
                .placeholder(circularProgressDrawable)
                .into(binding.moviePoster)

        }
    }
}