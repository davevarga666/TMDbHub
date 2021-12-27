package com.davevarga.tmdbmoviespaging.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.davevarga.tmdbmoviespaging.R
import com.davevarga.tmdbmoviespaging.databinding.FragmentComingSoonBinding
import com.davevarga.tmdbmoviespaging.models.Movie
import com.davevarga.tmdbmoviespaging.network.GetData
import com.davevarga.tmdbmoviespaging.network.ServiceBuilder
import com.davevarga.tmdbmoviespaging.repository.NetworkRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComingSoonFragment : Fragment(), MovieClickListener {

    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var binding: FragmentComingSoonBinding

    lateinit var networkRepository: NetworkRepository

    private val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            UpcomingViewModelFactory(
                networkRepository
            )
        )
            .get(UpcomingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().setTitle("Coming Soon")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_coming_soon, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService: GetData = ServiceBuilder.getNetworkClient(GetData::class.java)

        networkRepository = NetworkRepository(apiService)
        val movieAdapter = UpcomingMoviesAdapter(this)

        hideKeyboard()
        binding.upcomingRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.upcomingPagedList.observe(viewLifecycleOwner, Observer {
            movieAdapter.submitList(it)
            binding.upcomingRecyclerView.adapter = movieAdapter

        })


    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onItemClick(item: Movie, position: Int) {
        val action = ComingSoonFragmentDirections.actionComingSoonFragmentToDetailFragment(item)
        findNavController().navigate(action)

    }
}