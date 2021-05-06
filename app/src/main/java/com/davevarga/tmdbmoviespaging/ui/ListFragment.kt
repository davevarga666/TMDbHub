package com.davevarga.tmdbmoviespaging.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.davevarga.tmdbmoviespaging.R
import com.davevarga.tmdbmoviespaging.models.GenreString
import com.davevarga.tmdbmoviespaging.models.Movie
import com.davevarga.tmdbmoviespaging.network.GetData
import com.davevarga.tmdbmoviespaging.network.ServiceBuilder
import com.davevarga.tmdbmoviespaging.repository.NetworkRepository
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), MovieClickListener {

    private lateinit var swipeLayout: SwipeRefreshLayout

    val args: ListFragmentArgs by navArgs()

    lateinit var networkRepository: NetworkRepository
    private val compositeDisposable = CompositeDisposable()

    private val movieViewModel by lazy {
        ViewModelProviders.of(
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
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService: GetData = ServiceBuilder.getNetworkClient(GetData::class.java)

        Log.i("ARGS", args.genres)
        networkRepository = NetworkRepository(apiService)

        movieViewModel.refresh()
        movieViewModel.moviePagedList = networkRepository.fetchLiveMoviePagedList(compositeDisposable, args.minYear, args.maxYear, args.genres)
        hideKeyboard()



        recycler_view.apply {
            setHasFixedSize(true)
            adapter = movieAdapter
            layoutManager =  GridLayoutManager(
                context, // context
                2, // span count
                RecyclerView.VERTICAL, // orientation
                false // reverse layout
            )
        }

        swipeLayout = swipeRefresh

        movieViewModel.moviePagedList.observe(viewLifecycleOwner, Observer {
            movieAdapter.submitList(it)

        })

        swipeRefresh.setOnRefreshListener {
            movieViewModel.refresh()
            swipeRefresh.isRefreshing = false
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