package com.davevarga.tmdbhub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.davevarga.tmdbhub.adapter.GenreAdapter
import com.davevarga.tmdbhub.adapter.GenreAdapter.Companion.filledIdList
import com.davevarga.tmdbhub.R
import com.davevarga.tmdbhub.databinding.FragmentFilterBinding
import com.davevarga.tmdbhub.models.GenreString
import com.davevarga.tmdbhub.network.GetData
import com.davevarga.tmdbhub.network.ServiceBuilder
import com.davevarga.tmdbhub.repository.NetworkRepository

class FilterFragment : Fragment() {

    private lateinit var binding: FragmentFilterBinding
    private lateinit var networkRepository: NetworkRepository
    private var newGenreString: GenreString = GenreString(0, "27")
    private val viewModelAdapter = GenreAdapter(emptyList())

    private val genreViewModel by lazy {
        ViewModelProvider(
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
        filledIdList.clear()
        genreViewModel.genreList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                viewModelAdapter.items = it
                binding.genreRecyclerView.adapter = viewModelAdapter
            }
        })

        binding.genreRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = viewModelAdapter
        }


        binding.saveButton.setOnClickListener { view: View ->
            newGenreString.genres = filledIdList.joinToString("|")
            genreViewModel.insert(newGenreString)
            val toDisplay = newGenreString.genres

            val filterByYearAction = FilterFragmentDirections.actionFilterFragmentToListFragment(
                minYear = binding.minYearValue.text.toString(),
                maxYear = binding.maxYearValue.text.toString(),
                genres = toDisplay
            )
            view.findNavController().navigate(filterByYearAction)
        }
    }
}