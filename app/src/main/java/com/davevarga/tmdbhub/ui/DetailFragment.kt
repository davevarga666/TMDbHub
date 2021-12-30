package com.davevarga.tmdbhub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.davevarga.tmdbhub.BaseApplication.Companion.detailViewOpen
import com.davevarga.tmdbhub.R
import com.davevarga.tmdbhub.databinding.FragmentDetailBinding
import com.davevarga.tmdbhub.models.Movie

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    private val myCollectionViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            MyCollectionViewModelFactory(requireActivity().application)
        )
            .get(MyCollectionViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (detailViewOpen == true) {
            findNavController().popBackStack()
        }
        detailViewOpen = true
        requireActivity().setTitle("Details")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setBindings()
        setBackgroundImage(view)
    }

    private fun setBackgroundImage(view: View) {
        val backgroundPoster = args.movieDetails.backdropPath
        Glide.with(view)
            .load("http://image.tmdb.org/t/p/w500/" + backgroundPoster)
            .into(binding.backgroundPoster)
    }

    private fun setBindings() {
        binding.movie = args.movieDetails
        binding.addToCollection.setOnClickListener {
            myCollectionViewModel.insert(binding.movie as Movie)
        }
    }

}