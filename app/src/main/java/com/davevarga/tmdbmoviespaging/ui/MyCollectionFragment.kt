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
import com.davevarga.tmdbmoviespaging.db.AppDatabase
import com.davevarga.tmdbmoviespaging.models.Movie
import com.davevarga.tmdbmoviespaging.repository.MovieRepository
import dagger.hilt.android.AndroidEntryPoint

class MyCollectionFragment : Fragment(), MyCollectionClickListener {

    private lateinit var binding: FragmentMyMoviesBinding
    private val movieList: MutableList<Movie> = mutableListOf()
    private val viewModelAdapter = MyCollectionRecyclerAdapter(movieList, this)

    private val viewModel by lazy {
        ViewModelProviders.of(
            requireActivity(),
            MyCollectionViewModelFactory(requireActivity().application)
        )
            .get(MyCollectionViewModel::class.java)
    }

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

        setBindings()
        observeMovieModel()
    }

    private fun setBindings() {
        binding.myMoviesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
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
//        removeItem(position)
        val action = MyCollectionFragmentDirections.actionMyCollectionFragmentToDetailFragment(item)
        findNavController().navigate(action)

    }

    override fun onDeleteClick(item: Movie, position: Int) {
        viewModelAdapter.items.removeAt(position)
        viewModel.deleteMovie(item.id)
        binding.myMoviesRecyclerView.recycledViewPool.clear()
        viewModelAdapter.notifyDataSetChanged()
    }
}