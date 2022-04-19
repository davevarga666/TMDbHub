package com.davevarga.tmdbhub.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davevarga.tmdbhub.R
import com.davevarga.tmdbhub.adapter.MovieClickListener
import com.davevarga.tmdbhub.adapter.UpcomingMoviesAdapter
import com.davevarga.tmdbhub.databinding.FragmentComingSoonBinding
import com.davevarga.tmdbhub.models.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComingSoonFragment : BaseFragment<FragmentComingSoonBinding>(), MovieClickListener {

    private val viewModel: UpcomingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.comingsoon)
        val movieAdapter = UpcomingMoviesAdapter(this)
        hideKeyboard()
        setRecyclerview()
        observe(movieAdapter)
    }

    private fun observe(movieAdapter: UpcomingMoviesAdapter) {
        viewModel.upcomingPagedList.observe(viewLifecycleOwner, {
            movieAdapter.submitList(it)
            binding.upcomingRecyclerView.adapter = movieAdapter

        })
    }

    private fun setRecyclerview() {
        binding.upcomingRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onItemClick(item: Movie, position: Int) {
        val action = ComingSoonFragmentDirections.actionComingSoonFragmentToDetailFragment(item)
        findNavController().navigate(action)

    }

    override fun getFragmentView() = R.layout.fragment_coming_soon
}