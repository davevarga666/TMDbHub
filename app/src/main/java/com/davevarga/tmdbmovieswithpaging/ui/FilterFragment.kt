package com.davevarga.tmdbmovieswithpaging.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.davevarga.tmdbmovieswithpaging.R
import com.davevarga.tmdbmovieswithpaging.databinding.FragmentFilterBinding
import com.davevarga.tmdbmovieswithpaging.models.Years
import com.davevarga.tmdbmovieswithpaging.network.END_OF_YEAR
import com.davevarga.tmdbmovieswithpaging.network.START_OF_YEAR
import kotlinx.android.synthetic.main.fragment_filter.*


class FilterFragment : Fragment() {

    companion object {
        var range = Years("2019" + START_OF_YEAR, "2020" + END_OF_YEAR)
    }

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
            range.minYear = minYear_value.text.toString()
            range.maxYear = maxYear_value.text.toString()
            val filterByYearAction = FilterFragmentDirections.actionFilterFragmentToListFragment()
            view.findNavController().navigate(filterByYearAction)

        }
    }

}