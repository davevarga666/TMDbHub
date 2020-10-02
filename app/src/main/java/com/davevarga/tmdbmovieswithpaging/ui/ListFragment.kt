package com.davevarga.tmdbmovieswithpaging.ui

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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.davevarga.tmdbmovieswithpaging.R
import com.davevarga.tmdbmovieswithpaging.R.id.action_listFragment_to_filterFragment
import com.davevarga.tmdbmovieswithpaging.models.Movie
import com.davevarga.tmdbmovieswithpaging.network.GetData
import com.davevarga.tmdbmovieswithpaging.network.ServiceBuilder
import com.davevarga.tmdbmovieswithpaging.repository.MovieRepository
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), MovieClickListener {

    private lateinit var swipeLayout: SwipeRefreshLayout

    val args: ListFragmentArgs by navArgs()

    lateinit var movieRepository: MovieRepository

    private val viewModel by lazy {
        ViewModelProviders.of(
            requireActivity(),
            MovieViewModelFactory(
                movieRepository, args.minYear, args.maxYear
            )
        )
            .get(MovieViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService: GetData = ServiceBuilder.getNetworkClient(GetData::class.java)

        movieRepository = MovieRepository(apiService)
        val movieAdapter = MoviePagedlistAdapter(this)

        hideKeyboard()
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }
        swipeLayout = swipeRefresh

        viewModel.moviePagedList.observe(viewLifecycleOwner, Observer {
            movieAdapter.submitList(it)
            Log.d("ListFr", viewModel.minYear)
        })

        viewModel.refresh()
        filterButton.setOnClickListener { view: View ->
            view.findNavController().navigate(action_listFragment_to_filterFragment)

        }

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
        }

    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

//    fun Activity.hideKeyboard() {
//        hideKeyboard(currentFocus ?: View(this))
//    }

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