package com.davevarga.tmdbhub.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.davevarga.tmdbhub.BaseApplication.Companion.detailViewOpen
import com.davevarga.tmdbhub.R
import com.davevarga.tmdbhub.databinding.FragmentDetailBinding
import com.davevarga.tmdbhub.models.Movie
import com.davevarga.tmdbhub.util.BACKGROUND_BASE_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val args: DetailFragmentArgs by navArgs()
    private val myCollectionViewModel: MyCollectionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (detailViewOpen) {
            findNavController().popBackStack()
        }
        detailViewOpen = true
        requireActivity().title = getString(R.string.details)
        setHasOptionsMenu(true)
        setBindings()
        setBackgroundImage(view)
    }

    private fun setBackgroundImage(view: View) {
        val backgroundPoster = args.movieDetails.backdropPath
        Glide.with(view)
            .load(BACKGROUND_BASE_URL + backgroundPoster)
            .into(binding.backgroundPoster)
    }

    private fun setBindings() {
        binding.movie = args.movieDetails
        binding.addToCollection.setOnClickListener {
            myCollectionViewModel.insert(binding.movie as Movie)
        }
    }

    override fun getFragmentView() = R.layout.fragment_detail
}