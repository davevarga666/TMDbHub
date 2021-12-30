package com.davevarga.tmdbhub.ui

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
import com.davevarga.tmdbhub.BaseApplication.Companion.detailViewOpen
import com.davevarga.tmdbhub.R
import com.davevarga.tmdbhub.databinding.LayoutMovieListItemBinding
import com.davevarga.tmdbhub.models.Movie
import com.davevarga.tmdbhub.network.POSTER_BASE_URL

class MoviePagedlistAdapter(private var clickListener: MovieClickListener) :
    PagedListAdapter<Movie, MoviePagedlistAdapter.MyViewHolder>(MovieDiffCallback()) {

    lateinit var binding: LayoutMovieListItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view: View

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_movie_list_item,
            parent,
            false
        )


        return MyViewHolder(
            binding.root
        )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, binding, clickListener) }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            movieItem: Movie,
            binding: LayoutMovieListItemBinding,
            clickListener: MovieClickListener
        ) {
            binding.movieItem = movieItem

            itemView.setOnClickListener {
                detailViewOpen = false
                clickListener.onItemClick(movieItem, adapterPosition)
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
                .load(POSTER_BASE_URL + movieItem.posterPath)
                .error(R.drawable.error_image)
                .placeholder(circularProgressDrawable)
                .into(binding.moviePoster)

        }

    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }


}

interface MovieClickListener {
    fun onItemClick(item: Movie, position: Int)
}


