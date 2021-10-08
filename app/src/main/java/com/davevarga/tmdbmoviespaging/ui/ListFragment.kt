package com.davevarga.tmdbmoviespaging.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.davevarga.tmdbmoviespaging.R
import com.davevarga.tmdbmoviespaging.databinding.FragmentFilterBinding
import com.davevarga.tmdbmoviespaging.databinding.FragmentListBinding
import com.davevarga.tmdbmoviespaging.models.GenreString
import com.davevarga.tmdbmoviespaging.models.Movie
import com.davevarga.tmdbmoviespaging.network.GetData
import com.davevarga.tmdbmoviespaging.network.ServiceBuilder
import com.davevarga.tmdbmoviespaging.repository.NetworkRepository
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class ListFragment : Fragment(), MovieClickListener {

    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var binding: FragmentListBinding

    val args: ListFragmentArgs by navArgs()

    lateinit var networkRepository: NetworkRepository
    private val compositeDisposable = CompositeDisposable()

    private val movieViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            MovieViewModelFactory(
                requireActivity().application, networkRepository, args.minYear, args.maxYear, args.genres
            )
        )
            .get(MovieViewModel::class.java)
    }

    private val movieAdapter = MoviePagedlistAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().setTitle("Home")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_list, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService: GetData = ServiceBuilder.getNetworkClient(GetData::class.java)
        Log.i("ARGS", args.genres)
        networkRepository = NetworkRepository(apiService)
        movieViewModel.refresh()
        movieViewModel.moviePagedList = networkRepository.fetchLiveMoviePagedList(compositeDisposable, args.minYear, args.maxYear, args.genres)
        hideKeyboard()

        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = movieAdapter
            layoutManager =  GridLayoutManager(
                context, // context
                2, // span count
                RecyclerView.VERTICAL, // orientation
                false // reverse layout
            )
            // LinearLayoutManager(context)
        }

        swipeLayout = binding.swipeRefresh

        movieViewModel.moviePagedList.observe(viewLifecycleOwner, Observer {
            movieAdapter.submitList(it)

        })

        binding.swipeRefresh.setOnRefreshListener {
            movieViewModel.refresh()
            binding.swipeRefresh.isRefreshing = false
        }

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
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(item)
        findNavController().navigate(action)

    }


}