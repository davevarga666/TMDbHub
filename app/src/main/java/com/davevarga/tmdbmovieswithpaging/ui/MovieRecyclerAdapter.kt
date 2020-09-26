package com.davevarga.tmdbmovieswithpaging.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.davevarga.tmdbmovieswithpaging.R
import com.davevarga.tmdbmovieswithpaging.databinding.LayoutMovieListItemBinding
import com.davevarga.tmdbmovieswithpaging.models.Movie

class MovieRecyclerAdapter(var items: List<Movie>, var clickListener: MovieClickListener?) :
    RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {


    lateinit var binding: LayoutMovieListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_movie_list_item,
            parent,
            false
        )


        return MovieViewHolder(
            binding.root
        )

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        return holder.bind(items.get(position), binding, clickListener!!)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    //to remove duplicates
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            movieItem: Movie,
            binding: LayoutMovieListItemBinding,
            clickListener: MovieClickListener
        ) {
            binding.movieItem = movieItem

            itemView.setOnClickListener {
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
                .load("http://image.tmdb.org/t/p/w500/" + movieItem.posterPath)
                .error(R.drawable.error_image)
                .placeholder(circularProgressDrawable)
                .into(binding.moviePoster)

        }

    }

}

interface MovieClickListener {
    fun onItemClick(item: Movie, position: Int)
}

