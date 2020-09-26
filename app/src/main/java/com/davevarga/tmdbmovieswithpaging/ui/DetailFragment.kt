package com.davevarga.tmdbmovieswithpaging.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.davevarga.tmdbmovieswithpaging.R
import com.davevarga.tmdbmovieswithpaging.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.movie = args.movieDetails
        val backgroundPoster = args.movieDetails.backdropPath
        Glide.with(view)
            .load("http://image.tmdb.org/t/p/w500/" + backgroundPoster)
            .into(binding.backgroundPoster)
    }

}