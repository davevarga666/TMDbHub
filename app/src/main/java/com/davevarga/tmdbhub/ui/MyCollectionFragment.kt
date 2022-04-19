package com.davevarga.tmdbhub.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davevarga.tmdbhub.R
import com.davevarga.tmdbhub.adapter.MyCollectionClickListener
import com.davevarga.tmdbhub.adapter.MyCollectionRecyclerAdapter
import com.davevarga.tmdbhub.databinding.FragmentMyMoviesBinding
import com.davevarga.tmdbhub.models.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCollectionFragment : BaseFragment<FragmentMyMoviesBinding>(), MyCollectionClickListener {

    private val movieList: MutableList<Movie> = mutableListOf()
    private val viewModelAdapter = MyCollectionRecyclerAdapter(movieList, this)
    private val viewModel: MyCollectionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.mymovies)
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
        viewModel.myMovieList.observe(viewLifecycleOwner, { items ->
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

    override fun onDeleteClick(item: Movie, position: Int) {
        viewModelAdapter.items.removeAt(position)
        viewModel.deleteMovie(item.id)
        binding.myMoviesRecyclerView.recycledViewPool.clear()
        viewModelAdapter.notifyDataSetChanged()
    }

    override fun getFragmentView() = R.layout.fragment_my_movies
}