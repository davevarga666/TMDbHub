package com.davevarga.tmdbmoviespaging.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davevarga.tmdbmoviespaging.R
import com.davevarga.tmdbmoviespaging.databinding.FragmentMyMoviesBinding
import com.davevarga.tmdbmoviespaging.models.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCollectionFragment : Fragment(), MyCollectionClickListener {

    private lateinit var binding: FragmentMyMoviesBinding

    private val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            MyCollectionViewModelFactory(requireActivity().application)
        )
            .get(MyCollectionViewModel::class.java)
    }

    private val movieList: List<Movie> = arrayListOf()
    private val viewModelAdapter = MyCollectionRecyclerAdapter(movieList, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().setTitle("My Movies")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_my_movies, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        binding.myMoviesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
        observeMovieModel()
    }

    private fun observeMovieModel() {
        viewModel.myMovieList.observe(viewLifecycleOwner, Observer { items ->
            items?.apply {
                viewModelAdapter.items = items
                binding.myMoviesRecyclerView.adapter = viewModelAdapter
            }
        })

    }

    override fun onItemClick(item: Movie, position: Int) {
        val action = MyCollectionFragmentDirections.actionMyCollectionFragmentToDetailFragment(item)
        findNavController().navigate(action)

    }
}