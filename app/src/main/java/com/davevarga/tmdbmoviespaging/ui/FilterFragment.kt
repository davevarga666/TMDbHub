package com.davevarga.tmdbmoviespaging.ui

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.davevarga.tmdbmoviespaging.adapter.GenreAdapter
import com.davevarga.tmdbmoviespaging.adapter.GenreAdapter.Companion.filledIdList
import com.davevarga.tmdbmoviespaging.R
import com.davevarga.tmdbmoviespaging.databinding.FragmentFilterBinding
import com.davevarga.tmdbmoviespaging.models.Genre
import com.davevarga.tmdbmoviespaging.models.GenreString
import com.davevarga.tmdbmoviespaging.network.GetData
import com.davevarga.tmdbmoviespaging.network.ServiceBuilder
import com.davevarga.tmdbmoviespaging.repository.NetworkRepository
import kotlinx.android.synthetic.main.fragment_filter.*


class FilterFragment : Fragment() {

    private lateinit var binding: FragmentFilterBinding
    private var newGenreString: GenreString = GenreString(0, "27")

    lateinit var networkRepository: NetworkRepository
    private val genreList: List<Genre> = arrayListOf()
    private val viewModelAdapter = GenreAdapter(genreList)

    private val genreViewModel by lazy {
        ViewModelProviders.of(
            requireActivity(),
            GenreViewModelFactory(networkRepository, requireActivity().application)
        )
            .get(GenreViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().setTitle("Filter")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_filter, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService: GetData = ServiceBuilder.getNetworkClient(GetData::class.java)

        networkRepository = NetworkRepository(apiService)

        genreViewModel.getGenreList()
        viewModelAdapter.items = genreViewModel.genreList

        genreRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = viewModelAdapter
        }

        genreViewModel.genreList

        binding.saveButton.setOnClickListener { view: View ->

            newGenreString.genres = filledIdList.joinToString("|")
            Log.i("FILLED", filledIdList.toString())
            val toDisplay = newGenreString.genres

            val filterByYearAction = FilterFragmentDirections.actionFilterFragmentToListFragment(
                minYear = minYear_value.text.toString(),
                maxYear = maxYear_value.text.toString(),
                genres = toDisplay
            )
            view.findNavController().navigate(filterByYearAction)
        }
    }
    
}