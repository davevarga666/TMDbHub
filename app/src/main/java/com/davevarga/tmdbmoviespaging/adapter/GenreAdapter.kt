package com.davevarga.tmdbmoviespaging.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.davevarga.tmdbmoviespaging.databinding.LayoutGenreListItemBinding
import com.davevarga.tmdbmoviespaging.models.Genre


class GenreAdapter(var items: List<Genre>) :
    RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    lateinit var binding: LayoutGenreListItemBinding

    companion object {
        var filledIdList = mutableSetOf<String>()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        binding = DataBindingUtil.inflate(
            inflater,
            com.davevarga.tmdbmoviespaging.R.layout.layout_genre_list_item,
            parent,
            false
        )


        return GenreViewHolder(
            binding.root
        )

    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        return holder.bind(items.get(position), binding)
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

    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            genre: Genre,
            binding: LayoutGenreListItemBinding
        ) {
            binding.genre = genre

            binding.simpleCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    buttonView.isChecked = true
                    filledIdList.add(binding.genre?.genreId as String)
//                    filledIdList.add(Pair(binding.genre?.genreId, true))
                } else {
                    filledIdList.removeAll(filledIdList)
                }

            }

        }

    }
}

