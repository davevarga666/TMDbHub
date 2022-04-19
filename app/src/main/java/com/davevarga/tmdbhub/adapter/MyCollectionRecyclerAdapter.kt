package com.davevarga.tmdbhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.davevarga.tmdbhub.BaseApplication.Companion.detailViewOpen
import com.davevarga.tmdbhub.R
import com.davevarga.tmdbhub.databinding.LayoutCollectionListItemBinding
import com.davevarga.tmdbhub.models.Movie

class MyCollectionRecyclerAdapter(
    var items: MutableList<Movie>,
    var clickListener: MyCollectionClickListener?
) :
    RecyclerView.Adapter<MyCollectionRecyclerAdapter.MyCollectionViewHolder>() {


    private lateinit var binding: LayoutCollectionListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCollectionViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_collection_list_item,
            parent,
            false
        )


        return MyCollectionViewHolder(
            binding.root
        )

    }

    override fun onBindViewHolder(
        holder: MyCollectionViewHolder,
        position: Int
    ) {
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

    class MyCollectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            movieItem: Movie,
            binding: LayoutCollectionListItemBinding,
            clickListener: MyCollectionClickListener
        ) {
            binding.movieItem = movieItem

            itemView.setOnClickListener {
                detailViewOpen = false
                clickListener.onItemClick(movieItem, adapterPosition)
            }

            binding.remove.setOnClickListener {
                clickListener.onDeleteClick(movieItem, adapterPosition)
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

interface MyCollectionClickListener {
    fun onItemClick(item: Movie, position: Int)
    fun onDeleteClick(item: Movie, position: Int)
}