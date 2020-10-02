package com.davevarga.tmdbmovieswithpaging.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.davevarga.tmdbmovieswithpaging.R
import com.davevarga.tmdbmovieswithpaging.databinding.FragmentFilterBinding
import com.davevarga.tmdbmovieswithpaging.models.Years
import com.davevarga.tmdbmovieswithpaging.network.END_OF_YEAR
import com.davevarga.tmdbmovieswithpaging.network.START_OF_YEAR
import com.davevarga.tmdbmovieswithpaging.repository.MovieDataSource.Companion.maximumYear
import com.davevarga.tmdbmovieswithpaging.repository.MovieDataSource.Companion.minimumYear
import com.davevarga.tmdbmovieswithpaging.repository.MovieRepository
import kotlinx.android.synthetic.main.fragment_filter.*


class FilterFragment : Fragment() {

    private lateinit var binding: FragmentFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_filter, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener { view: View ->
            minimumYear = minYear_value.text.toString()
            maximumYear = maxYear_value.text.toString()
            val filterByYearAction = FilterFragmentDirections.actionFilterFragmentToListFragment(
//                minYear = minYear_value.text.toString(),
//                maxYear = maxYear_value.text.toString()
            )
            view.findNavController().navigate(filterByYearAction)
        }
    }

}